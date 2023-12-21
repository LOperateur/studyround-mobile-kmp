package com.studyround.app.ui.navigation

import cafe.adriel.voyager.core.screen.Screen

/**
 * Maps the supplied [Destination] to a [Screen]
 */
interface RouteMap {
    val destination: Destination
    fun getScreen(): Screen
}

interface Destination
