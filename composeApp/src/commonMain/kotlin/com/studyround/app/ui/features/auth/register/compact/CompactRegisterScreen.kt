package com.studyround.app.ui.features.auth.register.compact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.composables.common.StudyRoundBackground
import com.studyround.app.ui.composables.common.StudyRoundTextLogo
import com.studyround.app.ui.features.auth.register.RegisterFormContent
import com.studyround.app.ui.features.auth.register.RegisterTextFieldState
import com.studyround.app.ui.features.auth.register.RegisterViewEvent
import com.studyround.app.ui.features.auth.register.RegisterViewState
import com.studyround.app.utils.getString

@Composable
fun CompactRegisterScreen(
    viewState: RegisterViewState,
    textFieldState: RegisterTextFieldState,
    eventProcessor: (RegisterViewEvent) -> Unit,
) {
    StudyRoundBackground()

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding().imePadding()) {
        StudyRoundTextLogo(
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            ),
        )

        RegisterFormContent(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            usernameText = textFieldState.usernameText,
            passwordText = textFieldState.passwordText,
            passwordConfirmationText = textFieldState.passwordConfirmationText,
            usernameError = viewState.usernameError?.getString(),
            passwordError = viewState.passwordError?.getString(),
            passwordConfirmationError = viewState.passwordConfirmationError?.getString(),
            registrationLoading = viewState.registrationLoading,
            showCta = true,
            eventProcessor = eventProcessor,
        )
    }
}
