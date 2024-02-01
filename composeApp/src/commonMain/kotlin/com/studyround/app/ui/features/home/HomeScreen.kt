package com.studyround.app.ui.features.home

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.studyround.app.ui.features.dashboard.DashboardNavScreen

class HomeScreen: Screen {
    @Composable
    override fun Content() {
        Navigator(DashboardNavScreen())
    }
}
