package com.studyround.app.ui.features.main

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.studyround.app.ui.features.dashboard.DashboardScreen

@Composable
fun MainScreen() {
    Navigator(DashboardScreen())
}
