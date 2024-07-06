package com.studyround.app.data.error

import com.studyround.app.utils.AppString
import com.studyround.app.utils.AppStrings

class StudyRoundException(
    val error: AppError = AppError.GENERIC_ERROR,
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Exception()

enum class AppError(val message: AppStrings) {
    GENERIC_ERROR(AppStrings.SOMETHING_WRONG),
    CONNECTIVITY_ERROR(AppStrings.NO_CONNECTIVITY),
}

fun Throwable.renderedErrorMessage(): AppString {
    return when (this) {
        is StudyRoundException -> AppString(error.message)
        else -> AppString.textOrError(message)
    }
}
