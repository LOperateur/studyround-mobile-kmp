package com.studyround.app.ui.main

import com.studyround.app.ui.navigation.Destination
import kotlinx.serialization.Serializable

sealed class RootDestination : Destination {
    @Serializable
    data object Splash : RootDestination()

    @Serializable
    data object Onboarding : RootDestination()

    @Serializable
    data object Auth : RootDestination()

    @Serializable
    data object Main : RootDestination()

    @Serializable
    data object RegSurvey : RootDestination()
}
