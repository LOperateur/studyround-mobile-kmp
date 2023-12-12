package com.studyround.app.auth.otp

import com.studyround.app.storage.SettingsWrapper

class OtpProviderImpl(private val settingsWrapper: SettingsWrapper) : OtpProvider {
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
        settingsWrapper.setLastSavedPassToken("passToken")
    }
}
