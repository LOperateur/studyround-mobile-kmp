package com.studyround.app.ui.features.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.studyround.app.ui.composables.common.StudyRoundBackground
import com.studyround.app.ui.composables.common.StudyRoundTextLogo

class LoginScreen : Screen {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        val vm = getScreenModel<LoginViewModel>()
        val viewState by vm.viewState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val windowSizeClass = calculateWindowSizeClass()

        StudyRoundBackground()
        Box(
            modifier = Modifier.fillMaxSize().systemBarsPadding().imePadding()
        ) {
            Column(modifier = Modifier.matchParentSize()) {
                StudyRoundTextLogo(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

                if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
                    ExpandedLoginScreen()
                } else {
                    CompactLoginScreen(
                        viewState = viewState,
                        eventProcessor = vm::processEvent,
                    )
                }
            }
        }
    }

    @Composable
    private fun CompactLoginScreen(
        viewState: LoginViewState,
        eventProcessor: (LoginViewEvent) -> Unit,
    ) {
        Box {
            AnimatedVisibility(
                visible = !viewState.isSignUp,
                enter = slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = Ease,
                    ),
                    initialOffsetX = { -it },
                ),
                exit = slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = Ease,
                    ),
                    targetOffsetX = { -it },
                ),
            ) {
                LoginFormContent(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    eventProcessor = eventProcessor
                )
            }

            AnimatedVisibility(
                visible = viewState.isSignUp,
                enter = slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = Ease,
                    ),
                    initialOffsetX = { it },
                ),
                exit = slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = Ease,
                    ),
                    targetOffsetX = { it },
                ),
            ) {
                SignupFormContent(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    eventProcessor = eventProcessor
                )
            }
        }
    }

    @Composable
    private fun ExpandedLoginScreen() {

    }
}
