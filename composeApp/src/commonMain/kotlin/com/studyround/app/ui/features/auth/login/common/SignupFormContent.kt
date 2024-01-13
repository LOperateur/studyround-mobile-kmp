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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.studyround.app.MR
import com.studyround.app.ui.composables.buttons.LinkTextButton
import com.studyround.app.ui.composables.buttons.PlainButton
import com.studyround.app.ui.composables.buttons.PrimaryButton
import com.studyround.app.ui.composables.input.InputField
import com.studyround.app.ui.features.auth.login.GoToLoginClicked
import com.studyround.app.ui.features.auth.login.LoginViewEvent
import com.studyround.app.ui.theme.StudyRoundTheme
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SignupFormContent(
    modifier: Modifier = Modifier,
    eventProcessor: (LoginViewEvent) -> Unit,
    emailText: String = "",
    hideLoginButton: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()).padding(contentPadding),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(MR.strings.hello_greeting),
            fontFamily = StudyRoundTheme.typography.montserratFont,
            color = StudyRoundTheme.colors.deviation_primary1_white,
            style = StudyRoundTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal)
        )

        Spacer(modifier = Modifier.height(52.dp))

        InputField(
            modifier = Modifier.fillMaxWidth(0.75f),
            text = emailText,
            hint = stringResource(MR.strings.email_address_question),
            singleLine = true,
            maxLines = 1,
            action = ImeAction.Done,
            onValueChange = { },
        )

        Spacer(modifier = Modifier.height(32.dp))

        var termsAccepted by remember { mutableStateOf(false) }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = {
                    termsAccepted = it
                }
            )

            TermsAndConditionsText()
        }

        Spacer(modifier = Modifier.height(120.dp))

        PrimaryButton(
            text = stringResource(MR.strings.sign_up),
            textPadding = PaddingValues(horizontal = 24.dp)
        ) {

        }

        Spacer(modifier = Modifier.height(8.dp))

        PlainButton(
            textColor = StudyRoundTheme.colors.primary,
            backgroundColor = StudyRoundTheme.colors.deviation_white_tone5,
            text = stringResource(MR.strings.sign_up_google),
            iconStart = painterResource(MR.images.ic_google),
        ) {

        }

        if (!hideLoginButton) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 24.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                LinkTextButton(
                    text = stringResource(MR.strings.login_arrow),
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
    val terms = stringResource(MR.strings.terms_of_use)
    val policy = stringResource(MR.strings.privacy_policy)

    val termsTag = "terms"
    val policyTag = "policy"

    val disclaimer = stringResource(MR.strings.terms_and_privacy_disclaimer, terms, policy)
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
            text = stringResource(MR.strings.login_arrow),
        ) {
            eventProcessor(GoToLoginClicked)
        }
    }
}
