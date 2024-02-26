package com.studyround.app.ui.features.auth.otp

import com.studyround.app.utils.AppString
import com.studyround.app.utils.AppStrings

data class OtpViewState(
    val otpText: String = "",
    val resendOtpWaitSeconds: Int = 60,
    val hasResentOtp: Boolean = false,
    val isForgotPassword: Boolean = false,
    val otpValidationLoading: Boolean = false,
) {
    val title: AppString = if (isForgotPassword) {
        AppString(AppStrings.OTP_VERIFICATION)
    } else {
        AppString(AppStrings.EMAIL_CONFIRMATION)
    }
}
