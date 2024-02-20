package com.studyround.app.ui.features.auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.studyround.app.ui.features.auth.login.LoginScreen
import com.studyround.app.ui.features.auth.otp.OtpScreen

class AuthScreen : Screen {
    @Composable
    override fun Content() {
        Navigator(OtpScreen()) {
            SlideTransition(it)
        }
    }
}
