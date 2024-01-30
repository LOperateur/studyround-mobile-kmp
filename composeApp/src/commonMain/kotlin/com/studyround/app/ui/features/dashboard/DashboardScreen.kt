package com.studyround.app.ui.features.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class DashboardScreen : Screen {
    @Composable
    override fun Content() {
//        val navigator = LocalNavigator.currentOrThrow
        Box(modifier = Modifier.fillMaxSize())
    }
}
