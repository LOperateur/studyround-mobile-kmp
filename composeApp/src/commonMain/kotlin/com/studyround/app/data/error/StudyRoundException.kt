package com.studyround.app.data.error

import com.studyround.app.utils.AppString
import com.studyround.app.utils.AppStrings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class StudyRoundException(
    val error: StudyRoundError = AppError.GENERIC_ERROR
) : Exception()

sealed interface StudyRoundError

enum class AppError(val message: AppStrings): StudyRoundError {
    GENERIC_ERROR(AppStrings.SOMETHING_WRONG),
    CONNECTIVITY_ERROR(AppStrings.NO_CONNECTIVITY),
    EMPTY_RESPONSE_DATA(AppStrings.SOMETHING_WRONG),
}

@Serializable
data class ApiError(
    val status: Int,
    val message: String,
    val action: ErrorAction = ErrorAction.NOTHING,
): StudyRoundError

@Serializable
enum class ErrorAction {
    @SerialName("nothing")
    NOTHING,
    @SerialName("login")
    LOGIN,
}

fun Throwable.renderedErrorMessage(): AppString {
    return when (this) {
        is StudyRoundException -> {
            when (this.error) {
                is AppError -> AppString(appString = error.message)
                is ApiError -> AppString(dynamicText = error.message)
            }
        }
        else -> AppString.textOrError(message)
    }
}
