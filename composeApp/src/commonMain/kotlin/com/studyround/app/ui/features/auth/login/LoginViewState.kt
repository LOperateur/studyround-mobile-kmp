package com.studyround.app.ui.features.auth.login

import com.studyround.app.utils.AppString

data class LoginViewState(
    val isSignup: Boolean = false,
    val termsAccepted: Boolean = false,

    val emailUsernameError: AppString? = null,
    val passwordError: AppString? = null,
    val emailError: AppString? = null,

    val signupLoading: Boolean = false,
    val loginLoading: Boolean = false,
    val googleSignupLoading: Boolean = false,
    val googleLoginLoading: Boolean = false,
)

data class LoginTextFieldState(
    val emailUsernameText: String = "",
    val passwordText: String = "",
    val emailText: String = "",
)
