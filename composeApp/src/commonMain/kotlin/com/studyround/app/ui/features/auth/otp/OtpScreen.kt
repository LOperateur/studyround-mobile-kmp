package com.studyround.app.ui.features.auth.otp

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.studyround.app.ui.composables.alert.LocalAlertManager
import com.studyround.app.ui.features.auth.AuthRouteMap
import com.studyround.app.ui.features.auth.otp.compact.CompactOtpScreen
import com.studyround.app.ui.features.auth.otp.expanded.ExpandedOtpScreen
import com.studyround.app.ui.navigation.navigate
import com.studyround.app.ui.utils.isTabletLandscapeMode
import com.studyround.app.utils.mapToBundle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

class OtpScreen(private val args: Map<String, Any>) : Screen {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val vm = koinViewModel<OtpViewModel>(parameters = { parametersOf(args.mapToBundle()) } )
        val viewState by vm.viewState.collectAsState()
        val windowSizeClass = calculateWindowSizeClass()

        val authNavigator = LocalNavigator.currentOrThrow
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

                    is Navigate -> {
                        authNavigator.navigate(
                            AuthRouteMap(effect.destination).getScreen(),
                            effect.shouldReplace,
                        )
                    }

                    GoBack -> {
                        authNavigator.pop()
                    }
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
}
