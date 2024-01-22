package com.studyround.app.ui.features.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class OnboardingCarouselScreen : Screen {
    @Composable
    override fun Content() {
//        val navigator = LocalNavigator.currentOrThrow
        Box(modifier = Modifier.fillMaxSize())
    }
}
