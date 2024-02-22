package com.studyround.app.ui.features.auth.otp

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.studyround.app.ui.features.auth.otp.compact.CompactOtpScreen
import com.studyround.app.ui.features.auth.otp.expanded.ExpandedOtpScreen
import com.studyround.app.ui.utils.isTabletLandscapeMode

class OtpScreen : Screen {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val windowSizeClass = calculateWindowSizeClass()

        val isExpanded = windowSizeClass.isTabletLandscapeMode()

        Box {
            if (isExpanded) {
                ExpandedOtpScreen()
            } else {
                CompactOtpScreen()
            }
        }

    }
}
