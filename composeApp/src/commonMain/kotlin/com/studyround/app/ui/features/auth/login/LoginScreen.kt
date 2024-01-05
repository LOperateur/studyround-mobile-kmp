package com.studyround.app.ui.features.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.studyround.app.MR
import com.studyround.app.ui.composables.buttons.LinkTextButton
import com.studyround.app.ui.composables.buttons.PlainButton
import com.studyround.app.ui.composables.buttons.PrimaryButton
import com.studyround.app.ui.composables.common.StudyRoundBackground
import com.studyround.app.ui.composables.common.StudyRoundTextLogo
import com.studyround.app.ui.composables.input.InputField
import com.studyround.app.ui.composables.input.PasswordVisibilityToggleInputField
import com.studyround.app.ui.theme.StudyRoundTheme
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        StudyRoundBackground()
        Box(
            modifier = Modifier.fillMaxSize().systemBarsPadding()
                .imePadding().padding(horizontal = 16.dp)
        ) {
            Column(modifier = Modifier.matchParentSize()) {
                StudyRoundTextLogo(modifier = Modifier.padding(vertical = 8.dp))
                LoginFormContent()
            }
        }
    }

    @Composable
    private fun LoginFormContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(MR.strings.login),
                fontFamily = StudyRoundTheme.typography.montserratFont,
                color = StudyRoundTheme.colors.deviation_primary1_white,
                style = StudyRoundTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal)
            )

            Spacer(modifier = Modifier.height(52.dp))

            var emailText by remember { mutableStateOf("") }

            InputField(
                modifier = Modifier.fillMaxWidth(0.75f),
                text = emailText,
                hint = stringResource(MR.strings.email_username),
                singleLine = true,
                maxLines = 1,
                action = ImeAction.Next,
                onValueChange = { emailText = it },
            )

            Spacer(modifier = Modifier.height(28.dp))

            var passwordText by remember { mutableStateOf("") }

            PasswordVisibilityToggleInputField(
                modifier = Modifier.fillMaxWidth(0.75f),
                text = passwordText,
                hint = stringResource(MR.strings.password),
                onValueChange = { passwordText = it },
            )

            Spacer(modifier = Modifier.height(36.dp))

            LinkTextButton(text = stringResource(MR.strings.forgot_password_prompt)) {

            }

            Spacer(modifier = Modifier.height(36.dp))

            PrimaryButton(
                text = stringResource(MR.strings.login),
                textPadding = PaddingValues(horizontal = 24.dp)
            ) {

            }

            Spacer(modifier = Modifier.height(8.dp))

            PlainButton(
                textColor = StudyRoundTheme.colors.primary,
                backgroundColor = StudyRoundTheme.colors.deviation_white_tone5,
                text = stringResource(MR.strings.sign_in_google),
                iconStart = painterResource(MR.images.ic_google),
            ) {

            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 24.dp, bottom = 24.dp, end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                LinkTextButton(
                    text = stringResource(MR.strings.sign_up_arrow),
                    showUnderline = false,
                ) {

                }
            }
        }
    }
}
