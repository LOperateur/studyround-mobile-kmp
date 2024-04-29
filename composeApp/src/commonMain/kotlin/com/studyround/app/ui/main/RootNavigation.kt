package com.studyround.app.ui.main

import cafe.adriel.voyager.core.screen.Screen
import com.studyround.app.ui.features.auth.AuthScreen
import com.studyround.app.ui.features.home.HomeScreen
import com.studyround.app.ui.features.onboarding.OnboardingCarouselScreen
import com.studyround.app.ui.features.survey.RegSurveyScreen
import com.studyround.app.ui.navigation.Destination
import com.studyround.app.ui.navigation.RouteMap

sealed class RootDestination : Destination {
    data object Onboarding : RootDestination()
    data object Auth : RootDestination()
    data object Home : RootDestination()
    data object RegSurvey : RootDestination()
}

class RootRouteMap(override val destination: RootDestination) : RouteMap {
    override fun getScreen(): Screen {
        return when (destination) {
            RootDestination.Auth -> AuthScreen()
            RootDestination.Home -> HomeScreen()
            RootDestination.Onboarding -> OnboardingCarouselScreen()
            RootDestination.RegSurvey -> RegSurveyScreen()
        }
    }
}
