package com.studyround.app.ui.features.auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.studyround.app.ui.features.auth.login.LoginScreen

class AuthScreen : Screen {
    @Composable
    override fun Content() {
        Navigator(LoginScreen())
    }
}
