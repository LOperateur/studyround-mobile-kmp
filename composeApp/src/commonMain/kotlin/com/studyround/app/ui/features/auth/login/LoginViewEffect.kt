package com.studyround.app.ui.features.auth.login

import com.studyround.app.ui.features.auth.AuthDestination
import com.studyround.app.utils.AppString

sealed interface LoginViewEffect

data class ShowAlert(
    val message: AppString,
    val args: Array<Any> = arrayOf(),
) : LoginViewEffect

data class Navigate(val destination: AuthDestination, val replace: Boolean) : LoginViewEffect
