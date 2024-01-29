package com.studyround.app.data.remote.response

import co.touchlab.kermit.Logger
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyRoundResponse<T>(
    private val data: T? = null,
    private val errors: List<StudyRoundError>? = null,
    val message: String? = null,
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
                throw Exception(errors?.first()?.message)
            } else {
                this
            }
        }

    val dataOrThrow: T
        get() = data ?: throw Exception("No data in response")

    val dataAssertNoErrors: T
        get() {
            return if (hasErrors()) {
                errors?.forEach {
                    Logger.e(
                        messageString = it.toString(),
                        tag = "HTTPS Service Error",
                    )
                }
                throw Exception(errors?.first()?.message)
            } else {
                data ?: throw Exception("The server did not return any data")
            }
        }

    private fun hasErrors(): Boolean = !errors.isNullOrEmpty()
}

@Serializable
data class StudyRoundError(
    val status: Int,
    val message: String,
    val action: ErrorAction,
)

// TODO: Non-existent names could make this backwards incompatible
@Serializable
enum class ErrorAction {
    @SerialName("nothing")
    NOTHING,
    @SerialName("login")
    LOGIN,
}
