package com.studyround.app.ui.features.auth.register.expanded

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.composables.buttons.CircularIconButton
import com.studyround.app.ui.composables.common.StudyRoundBackground
import com.studyround.app.ui.composables.common.StudyRoundTextLogo
import com.studyround.app.ui.composables.modifiers.localSystemBarsPadding
import com.studyround.app.ui.features.auth.register.RegisterClicked
import com.studyround.app.ui.features.auth.register.RegisterFormContent
import com.studyround.app.ui.features.auth.register.RegisterTextFieldState
import com.studyround.app.ui.features.auth.register.RegisterViewEvent
import com.studyround.app.ui.features.auth.register.RegisterViewState
import com.studyround.app.ui.theme.StudyRoundTheme
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import org.jetbrains.compose.resources.painterResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.*

@Composable
fun ExpandedRegisterScreen(
    viewState: RegisterViewState,
    textFieldState: RegisterTextFieldState,
    eventProcessor: (RegisterViewEvent) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End,
    ) {
        StudyRoundBackground(
            modifier = Modifier.fillMaxWidth(0.5f)
                .background(color = StudyRoundTheme.colors.deviation_primary2_primary0),
            darkMode = true,
        )
    }

    Box(modifier = Modifier.fillMaxSize().localSystemBarsPadding().imePadding()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                StudyRoundTextLogo(
                    modifier = Modifier.padding(
                        vertical = 8.dp,
                        horizontal = 16.dp,
                    ),
                )

                RegisterFormContent(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 64.dp),
                    usernameText = textFieldState.usernameText,
                    passwordText = textFieldState.passwordText,
                    passwordConfirmationText = textFieldState.passwordConfirmationText,
                    usernameError = viewState.usernameError?.getString(),
                    passwordError = viewState.passwordError?.getString(),
                    passwordConfirmationError = viewState.passwordConfirmationError?.getString(),
                    registrationLoading = viewState.registrationLoading,
                    showCta = false,
                    eventProcessor = eventProcessor,
                )
            }

            Box(
                modifier = Modifier.weight(0.5f).fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                KamelImage(
                    modifier = Modifier.fillMaxHeight(0.5f).aspectRatio(1f),
                    resource = Resource.Success(painterResource(Res.drawable.illr_guy_msg)),
                    contentDescription = "Illustration",
                )
            }
        }

        CircularIconButton(
            modifier = Modifier.padding(bottom = 36.dp).size(64.dp).align(Alignment.BottomCenter),
            iconPadding = PaddingValues(0.dp),
            painter = painterResource(Res.drawable.ic_arrow_forward),
            iconColor = StudyRoundTheme.colors.white,
            showLoading = viewState.registrationLoading,
        ) {
            eventProcessor(RegisterClicked)
        }
    }
}
