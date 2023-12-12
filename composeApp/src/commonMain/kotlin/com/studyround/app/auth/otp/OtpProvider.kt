package com.studyround.app.auth.otp

interface OtpProvider {
    fun requestOtp(
        email: String,
        type: String,
        resend: Boolean,
        onResult: (otpId: Int?, Throwable?) -> Unit,
    )

    fun validateOtp(
        otpId: Int,
        otp: String,
        onResult: (passToken: String?, Throwable?) -> Unit,
    )
}
