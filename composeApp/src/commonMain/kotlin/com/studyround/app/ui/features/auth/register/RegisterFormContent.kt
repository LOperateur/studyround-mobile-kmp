package com.studyround.app.ui.features.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyround.app.ui.composables.buttons.CircularIconButton
import com.studyround.app.ui.composables.input.InputField
import com.studyround.app.ui.composables.input.PasswordVisibilityToggleInputField
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.*

@Composable
fun RegisterFormContent(
    modifier: Modifier = Modifier,
    usernameText: String,
    passwordText: String,
    passwordConfirmationText: String,
    usernameError: String?,
    passwordError: String?,
    passwordConfirmationError: String?,
    registrationLoading: Boolean,
    showCta: Boolean,
    eventProcessor: (RegisterViewEvent) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(Res.string.complete_registration),
                fontFamily = StudyRoundTheme.typography.montserratFont,
                lineHeight = 42.sp,
                color = StudyRoundTheme.colors.deviation_primary1_white,
                style = StudyRoundTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
            )

            Spacer(modifier = Modifier.height(36.dp))

            InputField(
                modifier = Modifier.fillMaxWidth(0.9f),
                text = usernameText,
                hint = stringResource(Res.string.username),
                singleLine = true,
                hasError = !usernameError.isNullOrEmpty(),
                maxLines = 1,
                action = ImeAction.Next,
                onValueChange = { eventProcessor(UsernameTextChanged(it)) },
            )

            Text(
                modifier = Modifier.padding(start = 16.dp).fillMaxWidth(),
                text = usernameError.orEmpty(),
                maxLines = 1,
                color = StudyRoundTheme.colors.danger,
                style = StudyRoundTheme.typography.labelSmall,
            )

            Spacer(modifier = Modifier.height(22.dp))

            PasswordVisibilityToggleInputField(
                modifier = Modifier.fillMaxWidth(0.9f),
                text = passwordText,
                hasError = !passwordError.isNullOrEmpty(),
                hint = stringResource(Res.string.password),
                action = ImeAction.Next,
                onValueChange = { eventProcessor(PasswordTextChanged(it)) },
            )

            Text(
                modifier = Modifier.padding(start = 16.dp).fillMaxWidth(),
                text = passwordError.orEmpty(),
                maxLines = 1,
                color = StudyRoundTheme.colors.danger,
                style = StudyRoundTheme.typography.labelSmall,
            )

            Spacer(modifier = Modifier.height(22.dp))

            PasswordVisibilityToggleInputField(
                modifier = Modifier.fillMaxWidth(0.9f),
                text = passwordConfirmationText,
                hasError = !passwordConfirmationError.isNullOrEmpty(),
                hint = stringResource(Res.string.confirm_password),
                onValueChange = { eventProcessor(PasswordConfirmationTextChanged(it)) },
            )

            Text(
                modifier = Modifier.padding(start = 16.dp).fillMaxWidth(),
                text = passwordConfirmationError.orEmpty(),
                maxLines = 1,
                color = StudyRoundTheme.colors.danger,
                style = StudyRoundTheme.typography.labelSmall,
            )

            Spacer(modifier = Modifier.height(48.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
        ) {
            if (showCta) {
                CircularIconButton(
                    modifier = Modifier.size(64.dp),
                    iconPadding = PaddingValues(0.dp),
                    painter = painterResource(Res.drawable.ic_arrow_forward),
                    iconColor = StudyRoundTheme.colors.white,
                    showLoading = registrationLoading,
                ) {
                    eventProcessor(RegisterClicked)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        RegisterFormContent(
            modifier = Modifier.fillMaxSize(),
            usernameText = "",
            passwordText = "",
            passwordConfirmationText = "",
            usernameError = null,
            passwordError = null,
            passwordConfirmationError = null,
            registrationLoading = false,
            showCta = true,
            eventProcessor = {},
        )
    }
}
