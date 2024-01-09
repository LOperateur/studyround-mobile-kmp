package com.studyround.app.ui.features.auth.login.compact

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.composables.common.StudyRoundBackground
import com.studyround.app.ui.composables.common.StudyRoundTextLogo
import com.studyround.app.ui.features.auth.login.LoginViewEvent
import com.studyround.app.ui.features.auth.login.LoginViewState
import com.studyround.app.ui.features.auth.login.common.LoginFormContent
import com.studyround.app.ui.features.auth.login.common.SignupFormContent

@Composable
fun CompactLoginScreen(
    viewState: LoginViewState,
    eventProcessor: (LoginViewEvent) -> Unit,
) {
    StudyRoundBackground()

    Column(modifier = Modifier.fillMaxSize()) {
        StudyRoundTextLogo(
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            )
        )

        Box {
            this@Column.AnimatedVisibility(
                visible = !viewState.isSignUp,
                enter = slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 600,
                        easing = EaseInOut,
                    ),
                    initialOffsetX = { -it },
                ),
                exit = slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = 600,
                        easing = EaseInOut,
                    ),
                    targetOffsetX = { -it },
                ),
            ) {
                LoginFormContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                    eventProcessor = eventProcessor
                )
            }

            this@Column.AnimatedVisibility(
                visible = viewState.isSignUp,
                enter = slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 600,
                        easing = EaseInOut,
                    ),
                    initialOffsetX = { it },
                ),
                exit = slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = 600,
                        easing = EaseInOut,
                    ),
                    targetOffsetX = { it },
                ),
            ) {
                SignupFormContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                    eventProcessor = eventProcessor
                )
            }
        }
    }
}
