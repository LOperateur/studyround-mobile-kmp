package com.studyround.app.ui.features.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.studyround.app.ui.composables.common.StudyRoundTextLogo

class DashboardNavScreen : Screen {
    @Composable
    override fun Content() {
//        val navigator = LocalNavigator.currentOrThrow
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            StudyRoundTextLogo()
        }
    }
}
