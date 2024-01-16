package com.studyround.app.ui.features.auth.login

sealed interface LoginViewEvent

data object GoToSignupClicked : LoginViewEvent

data object GoToLoginClicked : LoginViewEvent

data class EmailUsernameTextChanged(val emailUsername: String) : LoginViewEvent

data class PasswordTextChanged(val password: String) : LoginViewEvent

data class EmailTextChanged(val email: String) : LoginViewEvent

data object LoginClicked : LoginViewEvent

data object SignupClicked : LoginViewEvent

data class TermsToggled(val accepted: Boolean) : LoginViewEvent
