package com.studyround.app.ui.features.auth.otp

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.studyround.app.ui.features.auth.AuthDestination
import com.studyround.app.ui.features.auth.otp.compact.CompactOtpScreen
import com.studyround.app.ui.features.auth.otp.expanded.ExpandedOtpScreen
import com.studyround.app.ui.utils.isTabletLandscapeMode

class OtpScreen(private val args: Map<String, Boolean>) : Screen {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val vm = getScreenModel<OtpViewModel>()
        val viewState by vm.viewState.collectAsState()
        val windowSizeClass = calculateWindowSizeClass()

        val isExpanded = windowSizeClass.isTabletLandscapeMode()

        Box {
            if (isExpanded) {
                ExpandedOtpScreen(
                    viewState = viewState,
                    eventProcessor = vm::processEvent,
                )
            } else {
                CompactOtpScreen(
                    viewState = viewState,
                    eventProcessor = vm::processEvent,
                )
            }
        }

        LaunchedEffect(Unit) {
            vm.initPath(args[AuthDestination.OTP.FORGOT_PASSWORD] ?: false)
        }
    }
}
