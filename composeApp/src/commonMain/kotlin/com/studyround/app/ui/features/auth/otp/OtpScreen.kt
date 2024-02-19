package com.studyround.app.ui.features.auth.otp

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.studyround.app.ui.composables.input.OtpInputField

class OtpScreen(): Screen {
    @Composable
    override fun Content() {
        Column {
            var otpText by remember { mutableStateOf("") }
            OtpInputField(
                modifier = Modifier,
                value = otpText,
                onValueChange = {
                    otpText = it
                },
                onOtpEntered = {}
            )
        }
    }
}
