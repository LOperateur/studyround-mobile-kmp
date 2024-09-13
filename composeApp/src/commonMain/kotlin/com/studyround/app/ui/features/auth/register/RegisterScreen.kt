package com.studyround.app.ui.features.auth.register

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import com.studyround.app.ui.composables.alert.LocalAlertManager
import com.studyround.app.ui.features.auth.AuthDestination
import com.studyround.app.ui.features.auth.register.compact.CompactRegisterScreen
import com.studyround.app.ui.features.auth.register.expanded.ExpandedRegisterScreen
import com.studyround.app.ui.utils.isTabletLandscapeMode
import org.koin.compose.viewmodel.koinViewModel

class RegisterScreen(private val args: Map<String, Any>) : Screen {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val vm = koinViewModel<RegisterViewModel>()
        val viewState by vm.viewState.collectAsState()
        val textFieldState = vm.registerTextFieldState
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
                }
            }
        }

        Box {
            if (isExpanded) {
                ExpandedRegisterScreen(
                    viewState = viewState,
                    textFieldState = textFieldState,
                    eventProcessor = vm::processEvent,
                )
            } else {
                CompactRegisterScreen(
                    viewState = viewState,
                    textFieldState = textFieldState,
                    eventProcessor = vm::processEvent,
                )
            }
        }

        LaunchedEffect(Unit) {
            vm.initArgs(passToken = args[AuthDestination.Register.PASS_TOKEN] as? String)
        }
    }
}
