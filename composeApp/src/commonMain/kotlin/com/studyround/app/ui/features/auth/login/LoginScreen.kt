package com.studyround.app.ui.features.auth.login

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.studyround.app.ui.features.auth.login.compact.CompactLoginScreen
import com.studyround.app.ui.features.auth.login.expanded.ExpandedLoginScreen

class LoginScreen : Screen {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val vm = getScreenModel<LoginViewModel>()
        val viewState by vm.viewState.collectAsState()
        val textFieldState = vm.loginTextFieldState
//        val navigator = LocalNavigator.currentOrThrow
        val windowSizeClass = calculateWindowSizeClass()


        val isExpanded = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
                (windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium || windowSizeClass.heightSizeClass == WindowHeightSizeClass.Expanded)

        Box {
            if (isExpanded) {
                ExpandedLoginScreen(
                    viewState = viewState,
                    textFieldState = textFieldState,
                    eventProcessor = vm::processEvent,
                )
            } else {
                CompactLoginScreen(
                    viewState = viewState,
                    textFieldState = textFieldState,
                    eventProcessor = vm::processEvent,
                )
            }
        }
    }
}
