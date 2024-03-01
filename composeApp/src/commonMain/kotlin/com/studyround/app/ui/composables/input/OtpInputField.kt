package com.studyround.app.ui.composables.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.studyround.app.platform.ui.isBackspaceKeyEvent
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.painterResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.ic_backspace

@Composable
fun OtpInputField(
    modifier: Modifier = Modifier,
    value: String = "",
    numFields: Int = 4,
    onValueChange: (String) -> Unit,
    onOtpEntered: (String) -> Unit,
    hasError: Boolean = false,
    displayBackspaceButton: Boolean = true,
    focusManager: FocusManager = LocalFocusManager.current,
) {
    var otp = limitOtpDigits(value, numFields)
    val focusRequesters = List(numFields) { FocusRequester() }
    val lastIndex = numFields - 1

    // To control keyboard behavior
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier.padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.End) {
            // OTP Input
            Box {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    repeat(numFields) { index ->
                        SingleOtpInput(
                            digit = otp.toCharArray()[index].let {
                                if (it.isDigit()) it.toString().toInt() else null
                            },
                            hasError = hasError,
                            canEffectPaste = index == 0,
                            totalFields = numFields,
                            focusRequester = focusRequesters[index],
                            action = if (index == lastIndex) ImeAction.Done else ImeAction.Next,
                            onDigitEntered = {
                                otp = otp.take(index) + it.toString()[0] + otp.drop(index + 1)
                                onValueChange(otp.trimSpaces())

                                if (otp.trimSpaces().length == numFields) {
                                    focusManager.clearFocus()
                                    onOtpEntered(otp)
                                } else {
                                    val nextPosition = (index + 1).coerceAtMost(lastIndex)
                                    focusRequesters[nextPosition].requestFocus()
                                }
                            },
                            onDigitRemoved = {
                                otp = otp.take(index) + ' ' + otp.drop(index + 1)
                                onValueChange(otp.trimSpaces())
                            },
                            onDigitsPasted = {
                                // TODO: Support paste tooltip for input field even when fields are filled
                                //  Also migrate to BasicTextField2 to ensure last text field cursor
                                //  comes after the digit, not before. Wait for Compose MP 1.7
                                otp = it
                                onValueChange(otp)
                                focusRequesters[lastIndex].requestFocus()
                            },
                            onEmptyBackspacePressed = {
                                // Clear the last value
                                val lastFilledIndex = (otp.trimSpaces().length - 1).coerceAtLeast(0)
                                otp = otp.take(lastFilledIndex) + ' ' + otp.drop(lastFilledIndex + 1)

                                onValueChange(otp.trimSpaces())
                                resetFocus(otp,lastIndex, focusManager, focusRequesters, keyboardController)
                            }
                        )
                    }
                }

                Box(
                    modifier = Modifier.matchParentSize().clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                    ) {
                        resetFocus(otp, lastIndex, focusManager, focusRequesters, keyboardController)
                    }
                )
            }

            // Optional Backspace Button
            if (displayBackspaceButton) {
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    BackspaceButton {
                        // Clear the last value
                        val lastFilledIndex = (otp.trimSpaces().length - 1).coerceAtLeast(0)
                        otp = otp.take(lastFilledIndex) + ' ' + otp.drop(lastFilledIndex + 1)

                        onValueChange(otp.trimSpaces())
                        resetFocus(otp,lastIndex,focusManager,focusRequesters,keyboardController)
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        resetFocus(otp, lastIndex, focusManager, focusRequesters, keyboardController)
    }
}

@Composable
private fun SingleOtpInput(
    digit: Int?,
    hasError: Boolean,
    canEffectPaste: Boolean,
    totalFields: Int,
    action: ImeAction,
    focusRequester: FocusRequester,
    onDigitEntered: (Int) -> Unit,
    onDigitRemoved: () -> Unit,
    onDigitsPasted: (String) -> Unit,
    onEmptyBackspacePressed: () -> Unit,
) {
    InputField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .size(60.dp)
            .onPreviewKeyEvent {
                // TODO: Wait till when key event starts working on iOS
                //  https://github.com/JetBrains/compose-multiplatform/issues/4331
                if (digit == null && it.isBackspaceKeyEvent()) {
                    onEmptyBackspacePressed()
                    true
                } else {
                    false
                }
            },
        text = digit?.toString() ?: "",
        textStyle = StudyRoundTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
        maxLines = 1,
        keyboardType = KeyboardType.Number,
        shape = CircleShape,
        action = action,
        hasError = hasError,
        cursorColor = Color.Unspecified,
        onValueChange = { value ->
            if (value.isNotEmpty()) {
                if (canEffectPaste && isCopyPastedValue(value, totalFields)) {
                    onDigitsPasted(value)
                } else {
                    if (value.last().isDigit()) onDigitEntered(value.last().toString().toInt())
                }
            } else {
                onDigitRemoved()
            }
        },
    )
}

@Composable
private fun BackspaceButton(
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier.size(48.dp),
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier,
            painter = painterResource(Res.drawable.ic_backspace),
            tint = StudyRoundTheme.colors.deviation_secondary1_secondary3,
            contentDescription = null,
        )
    }
}

private fun resetFocus(
    otp: String,
    lastIndex: Int,
    focusManager: FocusManager,
    focusRequesters: List<FocusRequester>,
    keyboardController: SoftwareKeyboardController?,
) {
    val endPosition = otp.indexOf(' ').takeIf { it != -1 } ?: lastIndex
    focusManager.clearFocus()
    focusRequesters[endPosition].requestFocus()
    keyboardController?.show()
}

private fun limitOtpDigits(text: String, numFields: Int): String {
    val digits = text.filter { it.isDigit() }
    return if (digits.length >= numFields)
        digits.take(numFields) // Truncate to fit numFields
    else
        digits.padEnd(numFields, ' ') // Pad with spaces to match numFields
}

private fun isCopyPastedValue(text: String, numFields: Int): Boolean {
    return (text.length == numFields) && text.all { it.isDigit() }
}

private fun String.trimSpaces() = this.filter { it != ' ' }
