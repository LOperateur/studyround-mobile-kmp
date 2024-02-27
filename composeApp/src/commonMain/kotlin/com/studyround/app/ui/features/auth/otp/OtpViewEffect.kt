package com.studyround.app.ui.features.auth.otp

import com.studyround.app.ui.features.auth.AuthDestination
import com.studyround.app.utils.AppString

sealed interface OtpViewEffect

data class Navigate(val destination: AuthDestination, val shouldReplace: Boolean) : OtpViewEffect

data class ShowAlert(val message: AppString) : OtpViewEffect

data object GoBack : OtpViewEffect
