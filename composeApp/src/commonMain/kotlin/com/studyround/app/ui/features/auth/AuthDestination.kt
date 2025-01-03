package com.studyround.app.ui.features.auth

import com.studyround.app.ui.navigation.Destination
import kotlinx.serialization.Serializable

sealed class AuthDestination : Destination {
    @Serializable
    data object Login : AuthDestination()

    @Serializable
    data class OTP(
        val otpId: Int,
        val isForgotPassword: Boolean,
        val email: String,
    ) : AuthDestination()

    @Serializable
    data class Register(val passToken: String) : AuthDestination()

    @Serializable
    data object ResetPassword : AuthDestination()

    @Serializable
    data object ForgotPassword : AuthDestination()
}
