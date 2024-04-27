package com.studyround.app.ui.features.survey

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.studyround.app.ui.composables.alert.LocalAlertManager
import com.studyround.app.ui.features.home.HomeScreen
import com.studyround.app.ui.features.survey.compact.CompactRegSurveyScreen
import com.studyround.app.ui.features.survey.expanded.ExpandedRegSurveyScreen
import com.studyround.app.ui.navigation.navigate
import com.studyround.app.ui.utils.isTabletLandscapeMode

class RegSurveyScreen : Screen {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val vm = getScreenModel<RegSurveyViewModel>()
        val viewState by vm.viewState.collectAsState()
        val textFieldState = vm.registerTextFieldState
        val windowSizeClass = calculateWindowSizeClass()

        val alertManager = LocalAlertManager.current
        val isExpanded = windowSizeClass.isTabletLandscapeMode()
        val rootNavigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            vm.viewEffects.collect { effect ->
                when (effect) {
                    is ShowAlert -> {
                        alertManager.show(
                            effect.message.loadString(),
                            effect.type,
                        )
                    }

                    NavigateHome -> {
                        rootNavigator.navigate(HomeScreen(), true)
                    }
                }
            }
        }

        Box {
            if (isExpanded) {
                ExpandedRegSurveyScreen(
                    viewState = viewState,
                    textFieldState = textFieldState,
                    eventProcessor = vm::processEvent,
                )
            } else {
                CompactRegSurveyScreen(
                    viewState = viewState,
                    textFieldState = textFieldState,
                    eventProcessor = vm::processEvent,
                )
            }
        }
    }
}
