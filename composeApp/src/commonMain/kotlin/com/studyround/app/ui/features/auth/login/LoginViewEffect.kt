package com.studyround.app.ui.features.auth.login

interface LoginViewEffect

data class ShowAlert(val message: String) : LoginViewEffect
