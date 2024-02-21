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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.studyround.app.platform.ui.getPlatformContext
import com.studyround.app.ui.composables.buttons.LinkTextButton
import com.studyround.app.ui.composables.buttons.PlainButton
import com.studyround.app.ui.composables.buttons.SecondaryButton
import com.studyround.app.ui.composables.input.InputField
import com.studyround.app.ui.features.auth.login.EmailTextChanged
import com.studyround.app.ui.features.auth.login.GoToLoginClicked
import com.studyround.app.ui.features.auth.login.GoogleSignupClicked
import com.studyround.app.ui.features.auth.login.LoginViewEvent
import com.studyround.app.ui.features.auth.login.SignupClicked
import com.studyround.app.ui.features.auth.login.TermsToggled
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.*

@Composable
fun SignupFormContent(
    modifier: Modifier = Modifier,
    eventProcessor: (LoginViewEvent) -> Unit,
    emailText: String,
    emailError: String?,
    termsAccepted: Boolean,
    signupLoading: Boolean,
    signupGoogleLoading: Boolean,
    hideLoginButton: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()).padding(contentPadding),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(Res.string.hello_greeting),
            fontFamily = StudyRoundTheme.typography.montserratFont,
            color = StudyRoundTheme.colors.deviation_primary1_white,
            style = StudyRoundTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
        )

        Spacer(modifier = Modifier.height(52.dp))

        InputField(
            modifier = Modifier.fillMaxWidth(0.75f),
            text = emailText,
            hint = stringResource(Res.string.email_address_question),
            singleLine = true,
            hasError = !emailError.isNullOrEmpty(),
            action = ImeAction.Done,
            onValueChange = { eventProcessor(EmailTextChanged(it)) },
        )

        Text(
            modifier = Modifier.padding(start = 16.dp).fillMaxWidth(),
            text = emailError.orEmpty(),
            maxLines = 1,
            color = StudyRoundTheme.colors.danger,
            style = StudyRoundTheme.typography.labelSmall,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = {
                    eventProcessor(TermsToggled(it))
                }
            )

            TermsAndConditionsText()
        }

        Spacer(modifier = Modifier.height(120.dp))

        SecondaryButton(
            text = stringResource(Res.string.sign_up),
            textPadding = PaddingValues(horizontal = 24.dp),
            showLoading = signupLoading,
        ) {
            eventProcessor(SignupClicked)
        }

        Spacer(modifier = Modifier.height(8.dp))

        val context = getPlatformContext()

        PlainButton(
            textColor = StudyRoundTheme.colors.primary,
            backgroundColor = StudyRoundTheme.colors.deviation_white_tone5,
            showLoading = signupGoogleLoading,
            text = stringResource(Res.string.sign_up_google),
            iconStart = painterResource(Res.drawable.ic_google),
        ) {
            eventProcessor(GoogleSignupClicked(context))
        }

        if (!hideLoginButton) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 24.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                LinkTextButton(
                    text = stringResource(Res.string.login_arrow),
                    showUnderline = false,
                ) {
                    eventProcessor(GoToLoginClicked)
                }
            }
        }
    }
}

@Composable
private fun TermsAndConditionsText() {
    val terms = stringResource(Res.string.terms_of_use)
    val policy = stringResource(Res.string.privacy_policy)

    val termsTag = "terms"
    val policyTag = "policy"

    val disclaimer = stringResource(Res.string.terms_and_privacy_disclaimer, terms, policy)
    val linkStyle = SpanStyle(color = StudyRoundTheme.colors.deviation_primary1_primary4)

    val annotatedTermsAndPolicy = buildAnnotatedString {
        append(disclaimer)
        addStyle(linkStyle, disclaimer.indexOf(terms), disclaimer.indexOf(terms) + terms.length)
        addStringAnnotation(
            tag = termsTag,
            annotation = "https://studyround.com",
            start = disclaimer.indexOf(terms),
            end = disclaimer.indexOf(terms) + terms.length
        )

        addStyle(linkStyle, disclaimer.indexOf(policy), disclaimer.indexOf(policy) + policy.length)
        addStringAnnotation(
            tag = policyTag,
            annotation = "https://studyround.com",
            start = disclaimer.indexOf(policy),
            end = disclaimer.indexOf(policy) + policy.length
        )
    }

    ClickableText(
        text = annotatedTermsAndPolicy,
        style = StudyRoundTheme.typography.bodySmall.copy(color = StudyRoundTheme.colors.deviation_tone4_tone5),
        onClick = { offset ->
            annotatedTermsAndPolicy.getStringAnnotations(
                tag = termsTag,
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                Logger.d(tag = termsTag, messageString = it.item)
            }

            annotatedTermsAndPolicy.getStringAnnotations(
                tag = policyTag,
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                Logger.d(tag = policyTag, messageString = it.item)
            }
        }
    )
}

@Composable
fun GoToLoginLayout(
    modifier: Modifier = Modifier,
    eventProcessor: (LoginViewEvent) -> Unit,
) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = "Already have an account?",
            fontFamily = StudyRoundTheme.typography.montserratFont,
            color = StudyRoundTheme.colors.deviation_white_tone5,
            style = StudyRoundTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal)
        )

        PlainButton(
            textColor = StudyRoundTheme.colors.primary,
            backgroundColor = StudyRoundTheme.colors.deviation_white_tone5,
            text = stringResource(Res.string.login_arrow),
        ) {
            eventProcessor(GoToLoginClicked)
        }
    }
}
