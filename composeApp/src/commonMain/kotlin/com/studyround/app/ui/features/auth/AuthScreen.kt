package com.studyround.app.ui.features.auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.studyround.app.ui.features.survey.RegSurveyScreen

class AuthScreen : Screen {
    @Composable
    override fun Content() {
        Navigator(RegSurveyScreen()) {
            SlideTransition(it)
        }
    }
}
