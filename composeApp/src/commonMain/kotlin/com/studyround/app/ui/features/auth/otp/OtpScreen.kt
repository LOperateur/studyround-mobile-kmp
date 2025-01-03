package com.studyround.app.ui.features.auth.otp

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.studyround.app.ui.composables.alert.LocalAlertManager
import com.studyround.app.ui.features.auth.AuthDestination
import com.studyround.app.ui.features.auth.otp.compact.CompactOtpScreen
import com.studyround.app.ui.features.auth.otp.expanded.ExpandedOtpScreen
import com.studyround.app.ui.utils.isTabletLandscapeMode
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun OtpScreen(
    onNavigate: (destination: AuthDestination, shouldReplace: Boolean) -> Unit,
    onGoBack: () -> Unit,
) {
    val vm = koinViewModel<OtpViewModel>()
    val viewState by vm.viewState.collectAsState()
    val windowSizeClass = calculateWindowSizeClass()

    val alertManager = LocalAlertManager.current
    val isExpanded = windowSizeClass.isTabletLandscapeMode()

    LaunchedEffect(Unit) {
        vm.viewEffects.collect { effect ->
            when (effect) {
                is ShowAlert -> {
                    alertManager.show(
                        effect.message.loadString(),
                        effect.type,
                    )
                }

                is Navigate<*> -> {
                    onNavigate(effect.destination, effect.shouldReplace)
                }

                GoBack -> { onGoBack() }
            }
        }
    }

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
}
