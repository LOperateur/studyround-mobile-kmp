package com.studyround.app.ui.features.auth.login

import com.studyround.app.ui.composables.alert.AlertBannerType
import com.studyround.app.ui.features.auth.AuthDestination
import com.studyround.app.utils.AppString

sealed interface LoginViewEffect

data class ShowAlert(
    val message: AppString,
    val type: AlertBannerType,
    val args: Array<Any> = arrayOf(),
) : LoginViewEffect

data class Navigate<T : AuthDestination>(val destination: T, val shouldReplace: Boolean) : LoginViewEffect
