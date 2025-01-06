package com.studyround.app.ui.navigation

import kotlinx.serialization.Serializable

interface Destination

@Serializable
data object EmptyDestination: Destination
