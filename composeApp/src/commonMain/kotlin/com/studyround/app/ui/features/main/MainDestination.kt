package com.studyround.app.ui.features.main

import com.studyround.app.ui.navigation.Destination
import kotlinx.serialization.Serializable

sealed class MainDestination : Destination {
    @Serializable
    data object Dashboard : MainDestination()

    @Serializable
    data object Account : MainDestination()

    @Serializable
    data object Session : MainDestination()
}
