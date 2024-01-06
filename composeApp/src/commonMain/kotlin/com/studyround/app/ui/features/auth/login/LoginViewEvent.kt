package com.studyround.app.ui.features.auth.login

sealed interface LoginViewEvent

data object GoToSignupClicked : LoginViewEvent
data object GoToLoginClicked : LoginViewEvent
