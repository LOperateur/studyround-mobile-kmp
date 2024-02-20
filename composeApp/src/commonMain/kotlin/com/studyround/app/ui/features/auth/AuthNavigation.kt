package com.studyround.app.ui.features.auth

import cafe.adriel.voyager.core.screen.Screen
import com.studyround.app.ui.features.auth.login.LoginScreen
import com.studyround.app.ui.features.auth.otp.OtpScreen
import com.studyround.app.ui.navigation.Destination
import com.studyround.app.ui.navigation.RouteMap

sealed class AuthDestination : Destination {
    data object Login : AuthDestination()
    data object OTP : AuthDestination()
    data object Signup : AuthDestination()
    data object ResetPassword : AuthDestination()
    data object ForgotPassword : AuthDestination()
}

class AuthRouteMap(override val destination: AuthDestination) : RouteMap {
    override fun getScreen(): Screen {
        return when (destination) {
            AuthDestination.Login -> LoginScreen()
            AuthDestination.OTP -> OtpScreen()
            AuthDestination.Signup -> LoginScreen() // Todo
            AuthDestination.ResetPassword -> LoginScreen() // Todo
            AuthDestination.ForgotPassword -> LoginScreen() // Todo
        }
    }
}
