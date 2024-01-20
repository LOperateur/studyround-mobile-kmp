package com.studyround.app.ui.features.auth.login

data class LoginViewState(
    val isSignup: Boolean = false,
    val termsAccepted: Boolean = false,

    val emailUsernameError: String? = null,
    val passwordError: String? = null,
    val emailError: String? = null,

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
