package com.studyround.app.data.model.remote.response

import co.touchlab.kermit.Logger
import com.studyround.app.data.error.ApiError
import com.studyround.app.data.error.AppError
import com.studyround.app.data.error.StudyRoundException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyRoundResponse<T>(
    private val data: T? = null,
    private val errors: List<ApiError>? = null,
    val message: String? = null,
    val page: Int? = null,
    @SerialName("page_size") val pageSize: Int? = null,
    val total: Int? = null,
) {
    val assertNoErrors: StudyRoundResponse<T>
        get() {
            return if (hasErrors()) {
                errors?.forEach {
                    Logger.e(
                        messageString = it.toString(),
                        tag = "HTTPS Service Error",
                    )
                }
                errors?.first()?.let { throw StudyRoundException(it) }
                    ?: throw StudyRoundException()
            } else {
                this
            }
        }

    val dataOrThrow: T
        get() = data ?: throw StudyRoundException(AppError.EMPTY_RESPONSE_DATA)

    private fun hasErrors(): Boolean = !errors.isNullOrEmpty()
}
