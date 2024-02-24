package com.studyround.app.ui.features.auth.otp

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.composables.buttons.CircularIconButton
import com.studyround.app.ui.composables.buttons.LinkTextButton
import com.studyround.app.ui.composables.input.OtpInputField
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.*

@Composable
fun OtpFormContent(
    modifier: Modifier = Modifier,
    showCta: Boolean = false,
    title: String,
    otpResendWaitTime: Long,
    hasResentOtp: Boolean,
    eventProcessor: (OtpViewEvent) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title,
                fontFamily = StudyRoundTheme.typography.montserratFont,
                color = StudyRoundTheme.colors.deviation_primary1_white,
                style = StudyRoundTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(Res.string.otp_email_prompt),
                style = StudyRoundTheme.typography.bodySmall,
            )

            Spacer(modifier = Modifier.height(36.dp))

            var otpText by remember { mutableStateOf("") }

            OtpInputField(
                modifier = Modifier.fillMaxWidth(),
                value = otpText,
                onValueChange = {
                    otpText = it
                },
                onOtpEntered = {},
                displayBackspaceButton = true,
            )

            Spacer(modifier = Modifier.height(64.dp)) // Adjust for the bottom row
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            LinkTextButton(
                text = stringResource(Res.string.resend_otp),
                showUnderline = true,
            ) {

            }

            if (showCta) {
                CircularIconButton(
                    modifier = Modifier.size(64.dp),
                    iconPadding = PaddingValues(0.dp),
                    painter = painterResource(Res.drawable.ic_arrow_forward),
                    iconColor = StudyRoundTheme.colors.white,
                ) {

                }
            }
        }
    }
}
