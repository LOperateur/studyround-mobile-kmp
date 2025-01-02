package com.studyround.app.ui.features.home

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.studyround.app.ui.features.dashboard.DashboardNavScreen

@Composable
fun HomeScreen() {
    Navigator(DashboardNavScreen())
}
