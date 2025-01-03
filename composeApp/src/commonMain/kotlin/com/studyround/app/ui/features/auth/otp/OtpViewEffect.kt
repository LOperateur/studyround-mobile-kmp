package com.studyround.app.ui.features.auth.otp

import com.studyround.app.ui.composables.alert.AlertBannerType
import com.studyround.app.ui.features.auth.AuthDestination
import com.studyround.app.utils.AppString

sealed interface OtpViewEffect

data class Navigate<T: AuthDestination>(val destination: T, val shouldReplace: Boolean) : OtpViewEffect

data class ShowAlert(val message: AppString, val type: AlertBannerType) : OtpViewEffect

data object GoBack : OtpViewEffect
