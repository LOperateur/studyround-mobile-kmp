package com.studyround.app.ui.features.auth.login.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.studyround.app.MR
import com.studyround.app.ui.composables.buttons.LinkTextButton
import com.studyround.app.ui.composables.buttons.PlainButton
import com.studyround.app.ui.composables.buttons.PrimaryButton
import com.studyround.app.ui.composables.input.InputField
import com.studyround.app.ui.composables.input.PasswordVisibilityToggleInputField
import com.studyround.app.ui.features.auth.login.EmailUsernameTextChanged
import com.studyround.app.ui.features.auth.login.GoToSignupClicked
import com.studyround.app.ui.features.auth.login.LoginClicked
import com.studyround.app.ui.features.auth.login.LoginViewEvent
import com.studyround.app.ui.features.auth.login.PasswordTextChanged
import com.studyround.app.ui.theme.StudyRoundTheme
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun LoginFormContent(
    modifier: Modifier = Modifier,
    eventProcessor: (LoginViewEvent) -> Unit,
    emailUsernameText: String,
    passwordText: String,
    emailUsernameError: String?,
    passwordError: String?,
    hideSignupButton: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()).padding(contentPadding),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(MR.strings.login),
            fontFamily = StudyRoundTheme.typography.montserratFont,
            color = StudyRoundTheme.colors.deviation_primary1_white,
            style = StudyRoundTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
        )

        Spacer(modifier = Modifier.height(52.dp))

        InputField(
            modifier = Modifier.fillMaxWidth(0.75f),
            text = emailUsernameText,
            hint = stringResource(MR.strings.email_username),
            singleLine = true,
            hasError = !emailUsernameError.isNullOrEmpty(),
            maxLines = 1,
            action = ImeAction.Next,
            onValueChange = { eventProcessor(EmailUsernameTextChanged(it)) },
        )

        Spacer(modifier = Modifier.height(28.dp))

        PasswordVisibilityToggleInputField(
            modifier = Modifier.fillMaxWidth(0.75f),
            text = passwordText,
            hasError = !passwordError.isNullOrEmpty(),
            hint = stringResource(MR.strings.password),
            onValueChange = { eventProcessor(PasswordTextChanged(it)) },
        )

        Spacer(modifier = Modifier.height(36.dp))

        LinkTextButton(text = stringResource(MR.strings.forgot_password_prompt)) {

        }

        Spacer(modifier = Modifier.height(36.dp))

        PrimaryButton(
            text = stringResource(MR.strings.login),
            textPadding = PaddingValues(horizontal = 24.dp)
        ) {
            eventProcessor(LoginClicked)
        }

        Spacer(modifier = Modifier.height(8.dp))

        PlainButton(
            textColor = StudyRoundTheme.colors.primary,
            backgroundColor = StudyRoundTheme.colors.deviation_white_tone5,
            text = stringResource(MR.strings.sign_in_google),
            iconStart = painterResource(MR.images.ic_google),
        ) {

        }

        if (!hideSignupButton) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 24.dp, end = 16.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                LinkTextButton(
                    text = stringResource(MR.strings.sign_up_arrow),
                    showUnderline = false,
                ) {
                    eventProcessor(GoToSignupClicked)
                }
            }
        }
    }
}

@Composable
fun GoToSignupLayout(
    modifier: Modifier = Modifier,
    eventProcessor: (LoginViewEvent) -> Unit,
) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = "Don't have an account yet?",
            fontFamily = StudyRoundTheme.typography.montserratFont,
            color = StudyRoundTheme.colors.deviation_white_tone5,
            style = StudyRoundTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal)
        )

        PlainButton(
            textColor = StudyRoundTheme.colors.primary,
            backgroundColor = StudyRoundTheme.colors.deviation_white_tone5,
            text = stringResource(MR.strings.sign_up_arrow),
        ) {
            eventProcessor(GoToSignupClicked)
        }
    }
}
