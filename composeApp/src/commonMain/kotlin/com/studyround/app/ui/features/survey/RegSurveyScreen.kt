package com.studyround.app.ui.features.survey

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.studyround.app.ui.composables.alert.LocalAlertManager
import com.studyround.app.ui.features.survey.compact.CompactRegSurveyScreen
import com.studyround.app.ui.features.survey.expanded.ExpandedRegSurveyScreen
import com.studyround.app.ui.utils.isTabletLandscapeMode
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RegSurveyScreen(
    onNavigateToMainSection: () -> Unit,
) {
    val vm = koinViewModel<RegSurveyViewModel>()
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

                NavigateToMain -> { onNavigateToMainSection() }
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
