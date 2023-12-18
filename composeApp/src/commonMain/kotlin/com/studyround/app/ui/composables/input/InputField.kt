package com.studyround.app.ui.composables.input

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.theme.StudyRoundTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    hasError: Boolean = false,
    hint: String = "",
    action: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    focusManager: FocusManager = LocalFocusManager.current,
    singleLine: Boolean = false,
    maxLines: Int = 5,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    textColor: Color = StudyRoundTheme.colors.deviation_tone4_white,
    cursorColor: Color = StudyRoundTheme.colors.deviation_tone4_white,
    handleColor: Color = StudyRoundTheme.colors.deviation_primary1_white,
    selectionColor: Color = StudyRoundTheme.colors.deviation_primary1_white,
    focusedColor: Color = StudyRoundTheme.colors.primary2,
    errorColor: Color = StudyRoundTheme.colors.danger,
    hintColor: Color = StudyRoundTheme.colors.deviation_tone4_white.copy(alpha = 0.5f),
    backgroundColor: Color = StudyRoundTheme.colors.deviation_white_primary0,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    disabledColor: Color = StudyRoundTheme.colors.tone1,
    borderColor: Color = Color.Transparent,
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

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        TextField(
            modifier = modifier
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = color,
                    ),
                    shape = RoundedCornerShape(24.dp),
                )
                .clip(RoundedCornerShape(24.dp)),
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
            textStyle = StudyRoundTheme.typography.bodySmall,
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

///**
// * Password InputField that can toggle the visibility of the password
// * @param text [String] text on the inputText
// * @param onValueChange callback service that updates when the input text is updated
// * @param errorState [Boolean] to show the error state
// * @param hint [String] hint to show on the inputText, default to an empty String
// * @param action [ImeAction] defines the ImeAction, default to [ImeAction.Done]
// * @param focusManager used to help with the imeActionNext
// * @param defaultColor default color of outlined borders, default to [ZbColor.focus90]
// * @param focusedColor focused color of outlined borders, default to [ZbColor.peaceActive]
// * @param errorColor error color of outlined borders, default to [ZbColor.energy30]
// * @param backgroundColor color for the background of the inputField, default to [ZbColor.focusDeviation_00_10]
// */
//@Composable
//fun PasswordVisibilityToggleInputField(
//    modifier: Modifier = Modifier,
//    text: String,
//    onValueChange: (String) -> Unit,
//    errorState: Boolean = false,
//    hint: String = "",
//    action: ImeAction = ImeAction.Done,
//    focusManager: FocusManager = LocalFocusManager.current,
//    defaultColor: Color = ZbTheme.colors[ZbColor.focus90],
//    focusedColor: Color = ZbTheme.colors[ZbColor.peaceActive],
//    errorColor: Color = ZbTheme.colors[ZbColor.energy30],
//    backgroundColor: Color = ZbTheme.colors[ZbColor.focusDeviation_00_10],
//    borderColor: Color = ZbTheme.colors[ZbColor.focus90],
//    hintColor: Color = ZbTheme.colors[ZbColor.focus40],
//) {
//    val showPassword = rememberSaveable { mutableStateOf(false) }
//
//    var iconId = R.drawable.ic_visibility_hidden
//    var keyboardType = KeyboardType.Password
//
//    if (showPassword.value) {
//        iconId = R.drawable.ic_visibility_visible
//        keyboardType = KeyboardType.Text
//    }
//
//    val visualTransformation =
//        if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None
//
//    val icon = @Composable {
//        if (text.isNotEmpty()) {
//            IconButton(
//                onClick = {
//                    showPassword.value = !showPassword.value
//                },
//                modifier = Modifier.testTag(tag = "eyeIcon"),
//            ) {
//                Icon(
//                    painter = painterResource(iconId),
//                    contentDescription = null,
//                    tint = StudyRoundTheme.colors.tone1,
//                )
//            }
//        }
//    }
//
//    InputField(
//        modifier = modifier,
//        text = text,
//        onValueChange = onValueChange,
//        errorState = errorState,
//        hint = hint,
//        keyboardType = keyboardType,
//        action = action,
//        focusManager = focusManager,
//        visualTransformation = visualTransformation,
//        singleLine = true,
//        trailingIcon = icon,
//        defaultColor = defaultColor,
//        focusedColor = focusedColor,
//        errorColor = errorColor,
//        backgroundColor = backgroundColor,
//        borderColor = borderColor,
//        hintColor = hintColor,
//    )
//}

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
