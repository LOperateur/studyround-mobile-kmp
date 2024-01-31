package com.studyround.app.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

/**
 * When we don't have a default start destination for any [Screen] Navigator,
 * we use this Placeholder screen class.
 */
class PlaceholderScreen : Screen {
    @Composable
    override fun Content() {
        Box(modifier = Modifier.fillMaxSize())
    }
}
