package com.studyround.app.ui.features.auth.login

data class LoginViewState(
    val isSignUp: Boolean = false,
    val emailUsernameError: String? = null,
    val passwordError: String? = null,
    val emailError: String? = null,
)

data class LoginTextFieldState(
    val emailUsernameText: String = "",
    val passwordText: String = "",
    val emailText: String = "",
)
