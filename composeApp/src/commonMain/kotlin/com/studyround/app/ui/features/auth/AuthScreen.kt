package com.studyround.app.ui.features.auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.studyround.app.ui.features.auth.login.LoginScreen

@Composable
fun AuthScreen() {
    Navigator(LoginScreen()) {
        SlideTransition(it)
    }
}
