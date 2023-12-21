package com.studyround.app.auth.otp

import com.studyround.app.storage.AppPreferences

class OtpProviderImpl(private val appPreferences: AppPreferences) : OtpProvider {
    override fun requestOtp(
        email: String,
        type: String,
        resend: Boolean,
        onResult: (otpId: Int?, Throwable?) -> Unit
    ) {

    }

    override fun validateOtp(
        otpId: Int,
        otp: String,
        onResult: (passToken: String?, Throwable?) -> Unit
    ) {
        appPreferences.setLastSavedPassToken("passToken")
    }
}
