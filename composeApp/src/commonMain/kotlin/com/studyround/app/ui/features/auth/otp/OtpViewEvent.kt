package com.studyround.app.ui.features.auth.otp

sealed interface OtpViewEvent

data class OtpTextChanged(val otp: String) : OtpViewEvent

data object OtpSubmitted : OtpViewEvent

data object ResendOtpClicked : OtpViewEvent
