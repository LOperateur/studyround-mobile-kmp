package com.studyround.app.ui.features.auth.otp.compact

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
import com.studyround.app.ui.features.auth.otp.BackPressed
import com.studyround.app.ui.features.auth.otp.OtpFormContent
import com.studyround.app.ui.features.auth.otp.OtpViewEvent
import com.studyround.app.ui.features.auth.otp.OtpViewState
import com.studyround.app.utils.getString

@Composable
fun CompactOtpScreen(
    viewState: OtpViewState,
    eventProcessor: (OtpViewEvent) -> Unit,
) {
    StudyRoundBackground()

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding().imePadding()) {
        StudyRoundTextLogo(
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            ),
            onBackPressed = { eventProcessor(BackPressed) },
        )

        OtpFormContent(
            modifier = Modifier,
            otpText = viewState.otpText,
            title = viewState.title.getString(),
            hasResentOtp = viewState.hasResentOtp,
            resendOtpWaitSeconds = viewState.resendOtpWaitSeconds,
            otpValidationLoading = viewState.otpValidationLoading,
            showCta = true,
            eventProcessor = eventProcessor,
        )
    }
}
