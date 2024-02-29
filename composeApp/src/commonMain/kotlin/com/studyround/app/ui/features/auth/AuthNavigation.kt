package com.studyround.app.ui.features.auth

import cafe.adriel.voyager.core.screen.Screen
import com.studyround.app.ui.features.auth.login.LoginScreen
import com.studyround.app.ui.features.auth.otp.OtpScreen
import com.studyround.app.ui.features.auth.register.RegisterScreen
import com.studyround.app.ui.navigation.Destination
import com.studyround.app.ui.navigation.RouteMap

sealed class AuthDestination : Destination {
    data object Login : AuthDestination()
    data class OTP(override val args: Map<String, Any>) : AuthDestination() {
        companion object {
            const val OTP_ID = "otpId"
            const val FORGOT_PASSWORD = "forgotPassword"
            const val EMAIL = "email"
        }
    }
    data class Register(override val args: Map<String, Any>) : AuthDestination() {
        companion object {
            const val PASS_TOKEN = "passToken"
        }
    }
    data object ResetPassword : AuthDestination()
    data object ForgotPassword : AuthDestination()
}

class AuthRouteMap(
    override val destination: AuthDestination,
) : RouteMap {
    override fun getScreen(): Screen {
        return when (destination) {
            AuthDestination.Login -> LoginScreen()
            is AuthDestination.OTP -> OtpScreen(destination.args)
            is AuthDestination.Register -> RegisterScreen(destination.args)
            AuthDestination.ResetPassword -> LoginScreen() // Todo
            AuthDestination.ForgotPassword -> LoginScreen() // Todo
        }
    }
}
