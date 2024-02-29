package com.studyround.app.ui.features.auth.register

import com.studyround.app.utils.AppString

data class RegisterViewState(
    val registrationLoading: Boolean = false,

    val usernameError: AppString? = null,
    val passwordError: AppString? = null,
    val passwordConfirmationError: AppString? = null,
)

data class RegisterTextFieldState(
    val usernameText: String = "",
    val passwordText: String = "",
    val passwordConfirmationText: String = "",
)
