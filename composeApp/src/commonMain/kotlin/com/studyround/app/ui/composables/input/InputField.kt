package com.studyround.app.ui.composables.input

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.studyround.app.MR
import com.studyround.app.platform.ui.isBackspaceKeyEvent
import com.studyround.app.ui.neumorphic.LightSource
import com.studyround.app.ui.neumorphic.neumorphic
import com.studyround.app.ui.neumorphic.shape.Oval
import com.studyround.app.ui.neumorphic.shape.Pressed
import com.studyround.app.ui.neumorphic.shape.RoundedCorner
import com.studyround.app.ui.theme.StudyRoundTheme
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    hasError: Boolean = false,
    hint: String = "",
    action: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    focusManager: FocusManager = LocalFocusManager.current,
    singleLine: Boolean = false,
    maxLines: Int = 5,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    textStyle: TextStyle = StudyRoundTheme.typography.bodySmall,
    textColor: Color = StudyRoundTheme.colors.deviation_tone4_tone5,
    cursorColor: Color = StudyRoundTheme.colors.deviation_tone4_tone5,
    handleColor: Color = StudyRoundTheme.colors.deviation_primary1_white,
    selectionColor: Color = StudyRoundTheme.colors.deviation_primary1_white,
    focusedColor: Color = StudyRoundTheme.colors.primary2,
    errorColor: Color = StudyRoundTheme.colors.danger,
    hintColor: Color = StudyRoundTheme.colors.deviation_tone4_tone5.copy(alpha = 0.6f),
    backgroundColor: Color = StudyRoundTheme.colors.deviation_white_primary0,
    disabledColor: Color = StudyRoundTheme.colors.tone1,
    borderColor: Color = Color.Transparent,
    readOnly: Boolean = false,
    shape: Shape = RoundedCornerShape(28.dp),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = handleColor,
        backgroundColor = selectionColor.copy(0.5f),
    )

    // To control keyboard behavior
    val keyboardController = LocalSoftwareKeyboardController.current

    // To manage color state of outline
    val isFocus by interactionSource.collectIsFocusedAsState()
    val color = when {
        hasError -> errorColor
        isFocus -> focusedColor
        else -> borderColor
    }

    Box {
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            TextField(
                modifier = modifier
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = color,
                        ),
                        shape = shape,
                    )
                    .clip(shape)
                    .innerShadow(isFocus, shape),
                interactionSource = interactionSource,
                isError = hasError,
                visualTransformation = visualTransformation,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = action,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() },
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                ),
                value = text,
                textStyle = textStyle,
                onValueChange = {
                    onValueChange(it)
                },
                singleLine = singleLine,
                maxLines = maxLines,
                colors = defineTextFieldColors(
                    textColor = textColor,
                    backgroundColor = backgroundColor,
                    cursorColor = cursorColor,
                    disabledTextColor = disabledColor,
                ),
                label = if (hint.isNotBlank()) {
                    {
                        Text(
                            modifier = Modifier,
                            style = if (isFocus || text.isNotEmpty())
                                StudyRoundTheme.typography.labelSmall
                            else
                                StudyRoundTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
                            text = hint,
                            color = hintColor,
                        )
                    }
                } else null,
                placeholder = if (hint.isBlank()) placeholder else null,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                enabled = enabled,
                readOnly = readOnly,
            )
        }
    }
}

@Composable
fun PasswordVisibilityToggleInputField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    hasError: Boolean = false,
    hint: String = "",
    action: ImeAction = ImeAction.Done,
    focusManager: FocusManager = LocalFocusManager.current,
    textColor: Color = StudyRoundTheme.colors.deviation_tone4_tone5,
    focusedColor: Color = StudyRoundTheme.colors.primary2,
    errorColor: Color = StudyRoundTheme.colors.danger,
    hintColor: Color = StudyRoundTheme.colors.deviation_tone4_tone5.copy(alpha = 0.6f),
    backgroundColor: Color = StudyRoundTheme.colors.deviation_white_primary0,
    borderColor: Color = Color.Transparent,
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }

    var iconId = MR.images.ic_visibility_off
    var keyboardType = KeyboardType.Password

    if (showPassword) {
        iconId = MR.images.ic_visibility_on
        keyboardType = KeyboardType.Text
    }

    val visualTransformation =
        if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None

    val icon = @Composable {
        if (text.isNotEmpty()) {
            IconButton(
                onClick = {
                    showPassword = !showPassword
                },
            ) {
                Icon(
                    painter = painterResource(iconId),
                    contentDescription = null,
                    tint = StudyRoundTheme.colors.deviation_tone4_tone5,
                )
            }
        }
    }

    InputField(
        modifier = modifier,
        text = text,
        onValueChange = onValueChange,
        hasError = hasError,
        hint = hint,
        keyboardType = keyboardType,
        action = action,
        focusManager = focusManager,
        visualTransformation = visualTransformation,
        maxLines = 1,
        singleLine = true,
        trailingIcon = icon,
        textColor = textColor,
        focusedColor = focusedColor,
        errorColor = errorColor,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        hintColor = hintColor,
    )
}

// Todo: Handle BackSpace for iOS
@Composable
fun OtpInputField(
    modifier: Modifier = Modifier,
    numFields: Int = 4,
    onValueChange: (String) -> Unit,
    onOtpEntered: (String) -> Unit,
    hasError: Boolean = false,
    focusManager: FocusManager = LocalFocusManager.current,
) {
    var otp by remember { mutableStateOf(CharArray(numFields) { ' ' }.concatToString()) }
    val focusRequesters = List(numFields) { FocusRequester() }

    // To control keyboard behavior
    val keyboardController = LocalSoftwareKeyboardController.current

    Box {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            repeat(numFields) { index ->
                SingleOtpInput(
                    digit = otp.toCharArray()[index].let {
                        if (it.isDigit()) it.toString().toInt() else null
                    },
                    hasError = hasError,
                    focusRequester = focusRequesters[index],
                    action = if (index == numFields - 1) ImeAction.Done else ImeAction.Next,
                    onDigitEntered = {
                        otp = otp.take(index) + it.toString()[0] + otp.drop(index + 1)
                        onValueChange(otp.trimAll())

                        if (otp.trimAll().length == numFields) {
                            focusManager.clearFocus()
                            onOtpEntered(otp)
                        } else {
                            focusRequesters[index + 1].requestFocus()
                        }
                    },
                    onDigitRemoved = {
                        otp = otp.take(index) + ' ' + otp.drop(index + 1)

                        // TODO: Not necessary but put in place because of iOS
                        val prevPosition = (index - 1).coerceAtLeast(0)
                        focusRequesters[prevPosition].requestFocus()
                    },
                    onEmptyBackspacePressed = {
                        val prevPosition = (index - 1).coerceAtLeast(0)
                        focusRequesters[prevPosition].requestFocus()
                    }
                )
            }
        }

        Box(
            modifier = modifier.matchParentSize().clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                val nextPosition = otp.indexOf(' ').takeIf { it != -1 } ?: (numFields - 1)
                focusRequesters[nextPosition].requestFocus()
                keyboardController?.show()
            }
        )
    }

    LaunchedEffect(Unit) {
        focusRequesters.first().requestFocus()
    }
}

@Composable
private fun SingleOtpInput(
    digit: Int?,
    hasError: Boolean,
    action: ImeAction,
    focusRequester: FocusRequester,
    onDigitEntered: (Int) -> Unit,
    onDigitRemoved: () -> Unit,
    onEmptyBackspacePressed: () -> Unit,
) {
    // TODO: Enable full selection on focus
    InputField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .size(64.dp)
            .onKeyEvent {
                // TODO: Research When key event starts working on iOS
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
        onValueChange = { value ->
            if (value.isNotEmpty()) {
                if (value.last().isDigit()) onDigitEntered(value.last().toString().toInt())
            } else {
                onDigitRemoved()
            }
        },
    )
}

private fun String.trimAll() = this.filter { it != ' ' }

@Composable
fun defineTextFieldColors(
    textColor: Color,
    backgroundColor: Color,
    cursorColor: Color,
    disabledTextColor: Color,
): TextFieldColors {
    return TextFieldDefaults.textFieldColors(
        textColor = textColor,
        cursorColor = cursorColor,
        disabledTextColor = disabledTextColor,
        backgroundColor = backgroundColor,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        errorLabelColor = textColor,
        errorCursorColor = cursorColor,
    )
}

@Composable
fun Modifier.innerShadow(hideShadow: Boolean = false, shape: Shape): Modifier {
    return if (hideShadow || StudyRoundTheme.darkMode) {
        this
    } else {
        val cornerShape = when (shape) {
            CircleShape -> Oval
            else -> RoundedCorner(28.dp)
        }

        neumorphic(
            lightShadowColor = Color.White,
            darkShadowColor = Color.LightGray,
            lightSource = LightSource.LEFT_TOP,
            shadowElevation = 4.dp,
            shape = Pressed(cornerShape = cornerShape),
        )
    }
}
