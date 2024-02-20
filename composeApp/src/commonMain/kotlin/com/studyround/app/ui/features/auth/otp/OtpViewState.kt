package com.studyround.app.ui.features.auth.otp

data class OtpViewState(
    val otpText: String = "",
    val resendOtpWaitMillis: Long = 60_000L,
    val hasResentOtp: Boolean = false,
    val isForgotPassword: Boolean = false,
)
