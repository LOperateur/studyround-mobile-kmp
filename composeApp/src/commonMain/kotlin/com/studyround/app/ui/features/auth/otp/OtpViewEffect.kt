package com.studyround.app.ui.features.auth.otp

import com.studyround.app.ui.features.auth.AuthDestination

interface OtpViewEffect

data class Navigate(val destination: AuthDestination, val replace: Boolean) : OtpViewEffect
