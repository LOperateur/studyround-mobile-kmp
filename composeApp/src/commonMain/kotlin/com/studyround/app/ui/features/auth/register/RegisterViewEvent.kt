package com.studyround.app.ui.features.auth.register

sealed interface RegisterViewEvent

data object RegisterClicked : RegisterViewEvent

data class UsernameTextChanged(val username: String) : RegisterViewEvent

data class PasswordTextChanged(val password: String) : RegisterViewEvent

data class PasswordConfirmationTextChanged(val password: String) : RegisterViewEvent
