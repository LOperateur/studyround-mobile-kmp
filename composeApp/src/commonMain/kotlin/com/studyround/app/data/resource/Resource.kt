package com.studyround.app.data.resource

import co.touchlab.kermit.Logger
import com.studyround.app.data.model.remote.response.StudyRoundResponse
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningReduce
import kotlinx.datetime.Clock

sealed class Resource<T> {

    abstract val data: T?

    data class Success<T>(
        override val data: T,
        val message: String? = null,
        val pageData: PageData? = null,
    ) : Resource<T>()

    data class Loading<T>(
        override val data: T? = null,
    ) : Resource<T>()

    data class Error<T>(
        override val data: T? = null,
        val cause: Throwable,
    ) : Resource<T>()
}

/**
 * Catches any upstream exceptions (except [CancellationException]) and emits them as a
 * [Resource.Error].
 */
fun <T> Flow<Resource<T>>.catchErrorsAsResource(
    data: T? = null,
) = catch {
    if (it !is CancellationException) {
        Logger.e(messageString = it.message.orEmpty(), tag = "HTTPS Client Error")
        emit(Resource.Error(data = data, cause = it))
    }
}

/**
 * Create a cold [Flow] that executes the [resource] block and emits the status as [Resource]
 * values. The flow being cold means that the [resource] block is called every time a terminal
 * operator is applied to the resulting flow. This operator also applies the
 * [catchErrorsAsResource] operator.
 *
 * @param initialData The initial data provided in the loading resource, or in the error resource
 * if an exception is thrown.
 * @see wrappedResourceFlow
 * @see catchErrorsAsResource
 */
fun <T> resourceFlow(initialData: T? = null, resource: suspend () -> T) = flow {
    emit(Resource.Loading(initialData))
    emit(Resource.Success(resource()))
}.catchErrorsAsResource(initialData)

/**
 * Similar to [resourceFlow] but the executed [resource] is wrapped in a [StudyRoundResponse] in
 * order to pass an optional message or other metadata like pagination data.
 *
 * @param initialData The initial data provided in the loading resource, or in the error resource
 * if an exception is thrown.
 * @see resourceFlow
 */
fun <T> wrappedResourceFlow(initialData: T? = null, resource: suspend () -> StudyRoundResponse<T>) = flow {
    emit(Resource.Loading(initialData))
    val response = resource()
    emit(
        Resource.Success(
            response.dataOrThrow,
            message = response.message,
            pageData = PageData.fromStudyRoundResponse(response),
        )
    )
}.catchErrorsAsResource(initialData)

/**
 * Extension function to map the data in a Resource.
 *
 * @param T The type of the original data.
 * @param R The type of the transformed data.
 * @param transform A function that transforms the data from type T to type R.
 * @return A Resource containing the transformed data of type R.
 */
inline fun <T, R> Resource<T>.mapData(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(data), message, pageData)
        is Resource.Error -> Resource.Error(data?.let { transform(it) }, cause)
        is Resource.Loading -> Resource.Loading(data?.let { transform(it) })
    }
}

/**
 * Extension function to map the data in a list within a Resource.
 *
 * @param T The type of the original data in the list.
 * @param R The type of the transformed data in the list.
 * @param transform A function that transforms the data from type T to type R.
 * @return A Resource containing the transformed list data of type R.
 */
inline fun <T, R> Resource<List<T>>.mapListData(transform: (T) -> R): Resource<List<R>> {
    return this.mapData { list -> list.map(transform) }
}

/**
 * Delays emissions of [Resource.Loading] by a duration of [loadingWindow].  If a [Resource.Success]
 * or [Resource.Error] is emitted within the given window, the loading resource will not be emitted
 * downstream.  If the loading window expires before another item is emitted, any items that come
 * after will be delayed until at least a duration of [minLoadTime] has passed since the loading
 * resource was emitted.
 */
@OptIn(FlowPreview::class)
fun <T> Flow<Resource<T>>.windowedLoadDebounce(
    loadingWindow: Long = 200L,
    minLoadTime: Long = 1000L,
): Flow<Resource<T>> = debounce {
    when (it) {
        is Resource.Loading -> loadingWindow
        else -> 0L
    }
}.map {
    it to Clock.System.now().toEpochMilliseconds()
}.runningReduce { previous, current ->
    if (previous.first is Resource.Loading) {
        val timeLoadingActual = Clock.System.now().toEpochMilliseconds() - previous.second
        val delay = (minLoadTime - timeLoadingActual).coerceAtLeast(0L)
        delay(delay)
    }
    current
}.map {
    it.first
}
