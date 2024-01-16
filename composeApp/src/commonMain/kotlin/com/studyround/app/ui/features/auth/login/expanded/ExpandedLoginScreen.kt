package com.studyround.app.ui.features.auth.login.expanded

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.composables.common.StudyRoundBackground
import com.studyround.app.ui.composables.common.StudyRoundTextLogo
import com.studyround.app.ui.features.auth.login.common.GoToLoginLayout
import com.studyround.app.ui.features.auth.login.common.GoToSignupLayout
import com.studyround.app.ui.features.auth.login.common.LoginFormContent
import com.studyround.app.ui.features.auth.login.LoginViewEvent
import com.studyround.app.ui.features.auth.login.LoginViewState
import com.studyround.app.ui.features.auth.login.LoginTextFieldState
import com.studyround.app.ui.features.auth.login.common.SignupFormContent
import com.studyround.app.ui.theme.StudyRoundTheme

@Composable
fun ExpandedLoginScreen(
    viewState: LoginViewState,
    textFieldState: LoginTextFieldState,
    eventProcessor: (LoginViewEvent) -> Unit,
) {
    Row(modifier = Modifier.fillMaxSize()) {
        StudyRoundBackground(
            modifier = Modifier.weight(0.5f)
                .background(color = StudyRoundTheme.colors.deviation_primary2_primary0),
            darkMode = true,
        )
        StudyRoundBackground(
            modifier = Modifier.weight(0.5f)
                .background(color = StudyRoundTheme.colors.deviation_primary2_primary0),
            darkMode = true,
        )
    }

    Box(modifier = Modifier.fillMaxSize().systemBarsPadding().imePadding()) {
        Row {
            // Left side
            Box(modifier = Modifier.weight(0.5f)) {
                this@Row.AnimatedVisibility(
                    visible = !viewState.isSignup,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    GoToSignupLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 64.dp)
                            .padding(top = 52.dp), // Adjust for StudyRound logo,
                        eventProcessor = eventProcessor
                    )
                }

                this@Row.AnimatedVisibility(
                    visible = viewState.isSignup,
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
                    SignupFormContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = StudyRoundTheme.colors.deviation_tone1_primary1)
                            .padding(horizontal = 64.dp)
                            .padding(top = 52.dp), // Adjust for StudyRound logo
                        emailText = textFieldState.emailText,
                        emailError = viewState.emailError,
                        termsAccepted = viewState.termsAccepted,
                        eventProcessor = eventProcessor,
                        hideLoginButton = true,
                        contentPadding = PaddingValues(bottom = 24.dp)
                    )
                }
            }

            // Right Side
            Box(modifier = Modifier.weight(0.5f)) {
                this@Row.AnimatedVisibility(
                    visible = viewState.isSignup,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    GoToLoginLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 64.dp)
                            .padding(top = 52.dp), // Match other side adjusting for StudyRound Logo
                        eventProcessor = eventProcessor
                    )
                }

                this@Row.AnimatedVisibility(
                    visible = !viewState.isSignup,
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
                    LoginFormContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = StudyRoundTheme.colors.deviation_tone1_primary1)
                            .padding(horizontal = 64.dp),
                        emailUsernameText = textFieldState.emailUsernameText,
                        passwordText = textFieldState.passwordText,
                        emailUsernameError = viewState.emailUsernameError,
                        passwordError = viewState.passwordError,
                        eventProcessor = eventProcessor,
                        hideSignupButton = true,
                        contentPadding = PaddingValues(
                            top = 52.dp,
                            bottom = 24.dp,
                        ), // 52.dp to Match other side adjusting for StudyRound Logo
                    )
                }
            }
        }

        val animatedColor by animateColorAsState(
            targetValue = if (viewState.isSignup) StudyRoundTheme.colors.deviation_tone4_tone5 else StudyRoundTheme.colors.deviation_white_tone5,
            animationSpec = tween(delayMillis = 300)
        )

        val logoTextColor = if (StudyRoundTheme.darkMode)
            StudyRoundTheme.colors.deviation_tone4_tone5
        else {
            animatedColor
        }

        StudyRoundTextLogo(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            textColor = logoTextColor,
        )
    }
}
