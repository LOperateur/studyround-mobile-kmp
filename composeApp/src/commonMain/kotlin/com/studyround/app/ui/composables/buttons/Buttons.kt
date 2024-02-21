package com.studyround.app.ui.composables.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.theme.StudyRoundTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    textPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    showLoading: Boolean = false,
    enabled: Boolean = true,
    iconStart: Painter? = null,
    iconEnd: Painter? = null,
    onClick: (text: String) -> Unit,
) {
    BasicGradientButton(
        modifier = modifier,
        text = text,
        textPadding = textPadding,
        brush = Brush.horizontalGradient(
            colors = listOf(
                StudyRoundTheme.colors.primary1,
                StudyRoundTheme.colors.primary2,
            )
        ),
        showLoading = showLoading,
        enabled = enabled,
        iconStart = iconStart,
        iconEnd = iconEnd,
        onClick = onClick,
    )
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    textPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    showLoading: Boolean = false,
    enabled: Boolean = true,
    iconStart: Painter? = null,
    iconEnd: Painter? = null,
    onClick: (text: String) -> Unit,
) {
    BasicGradientButton(
        modifier = modifier,
        text = text,
        textPadding = textPadding,
        brush = Brush.horizontalGradient(
            colors = listOf(
                StudyRoundTheme.colors.secondary1,
                StudyRoundTheme.colors.secondary3,
            )
        ),
        showLoading = showLoading,
        enabled = enabled,
        iconStart = iconStart,
        iconEnd = iconEnd,
        onClick = onClick,
    )
}

@Composable
fun PrimaryOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    textPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    showLoading: Boolean = false,
    enabled: Boolean = true,
    iconStart: Painter? = null,
    iconEnd: Painter? = null,
    onClick: (text: String) -> Unit,
) {
    BasicOutlinedButton(
        modifier = modifier,
        text = text,
        textPadding = textPadding,
        color = StudyRoundTheme.colors.primary,
        showLoading = showLoading,
        enabled = enabled,
        iconStart = iconStart,
        iconEnd = iconEnd,
        onClick = onClick,
    )
}

@Composable
fun SecondaryOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    textPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    showLoading: Boolean = false,
    enabled: Boolean = true,
    iconStart: Painter? = null,
    iconEnd: Painter? = null,
    onClick: (text: String) -> Unit,
) {
    BasicOutlinedButton(
        modifier = modifier,
        text = text,
        textPadding = textPadding,
        color = StudyRoundTheme.colors.secondary,
        showLoading = showLoading,
        enabled = enabled,
        iconStart = iconStart,
        iconEnd = iconEnd,
        onClick = onClick,
    )
}

@Composable
fun LinkTextButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = StudyRoundTheme.typography.bodySmall,
    textColor: Color = StudyRoundTheme.colors.deviation_tone4_tone5,
    showUnderline: Boolean = true,
    enabled: Boolean = true,
    onClick: (text: String) -> Unit,
) {
    TextButton(
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = textColor,
            disabledContentColor = StudyRoundTheme.colors.black.copy(alpha = 0.5f),
        ),
        onClick = { onClick(text) },
    ) {
        Text(
            text = text,
            style = textStyle,
            textDecoration = if (showUnderline) TextDecoration.Underline else null,
        )
    }
}

@Composable
fun PlainButton(
    modifier: Modifier = Modifier,
    text: String,
    textPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    backgroundColor: Color,
    textColor: Color,
    showLoading: Boolean = false,
    enabled: Boolean = true,
    iconStart: Painter? = null,
    iconEnd: Painter? = null,
    onClick: (text: String) -> Unit,
) {
    Button(
        modifier = modifier.defaultMinSize(minHeight = 42.dp),
        shape = RoundedCornerShape(24.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            contentColor = textColor,
            backgroundColor = backgroundColor,
            disabledContentColor = StudyRoundTheme.colors.black.copy(alpha = 0.5f),
            disabledBackgroundColor = StudyRoundTheme.colors.gray,
        ),
        onClick = { if (!showLoading) onClick(text) },
    ) {
        ButtonContent(
            text = text,
            textPadding = textPadding,
            showLoading = showLoading,
            loadingColor = textColor,
            iconStart = iconStart,
            iconEnd = iconEnd,
        )
    }
}

@Composable
fun CircularIconButton(
    modifier: Modifier = Modifier,
    iconPadding: PaddingValues = PaddingValues(4.dp),
    painter: Painter,
    showLoading: Boolean = false,
    iconColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    GradientButton(
        modifier = modifier.defaultMinSize(minHeight = 42.dp),
        shape = CircleShape,
        brush = Brush.horizontalGradient(
            colors = listOf(
                StudyRoundTheme.colors.secondary1,
                StudyRoundTheme.colors.secondary3,
            )
        ),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            contentColor = StudyRoundTheme.colors.white,
            disabledContentColor = StudyRoundTheme.colors.black.copy(alpha = 0.5f),
            disabledBackgroundColor = StudyRoundTheme.colors.gray,
        ),
        onClick = { if (!showLoading) onClick() },
    ) {
        val iconAlpha = if (showLoading) 0f else 1f

        Box(
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painter,
                modifier = Modifier.fillMaxSize().padding(iconPadding).alpha(iconAlpha),
                tint = iconColor,
                contentDescription = "",
            )

            if (showLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                    color = iconColor,
                )
            }
        }
    }
}

// region Private Button Composables

@Composable
private fun BasicGradientButton(
    modifier: Modifier = Modifier,
    text: String,
    textPadding: PaddingValues,
    brush: Brush,
    showLoading: Boolean = false,
    enabled: Boolean = true,
    iconStart: Painter? = null,
    iconEnd: Painter? = null,
    onClick: (text: String) -> Unit,
) {
    GradientButton(
        modifier = modifier.defaultMinSize(minHeight = 42.dp),
        shape = RoundedCornerShape(24.dp),
        brush = brush,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            contentColor = StudyRoundTheme.colors.white,
            disabledContentColor = StudyRoundTheme.colors.black.copy(alpha = 0.5f),
            disabledBackgroundColor = StudyRoundTheme.colors.gray,
        ),
        onClick = { if (!showLoading) onClick(text) },
    ) {
        ButtonContent(
            text = text,
            textPadding = textPadding,
            showLoading = showLoading,
            loadingColor = StudyRoundTheme.colors.white,
            iconStart = iconStart,
            iconEnd = iconEnd,
        )
    }
}

@Composable
private fun BasicOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    textPadding: PaddingValues,
    color: Color,
    showLoading: Boolean = false,
    enabled: Boolean = true,
    iconStart: Painter? = null,
    iconEnd: Painter? = null,
    onClick: (text: String) -> Unit,
) {
    OutlinedButton(
        modifier = modifier.defaultMinSize(minHeight = 42.dp),
        shape = RoundedCornerShape(24.dp),
        enabled = enabled,
        border = BorderStroke(1.dp, if (enabled) color else StudyRoundTheme.colors.gray),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = color,
            backgroundColor = Color.Transparent,
            disabledContentColor = StudyRoundTheme.colors.black.copy(alpha = 0.5f),
        ),
        onClick = { if (!showLoading) onClick(text) },
    ) {
        ButtonContent(
            text = text,
            textPadding = textPadding,
            showLoading = showLoading,
            loadingColor = color,
            iconStart = iconStart,
            iconEnd = iconEnd,
        )
    }
}

@Composable
private fun ButtonContent(
    text: String,
    textPadding: PaddingValues,
    showLoading: Boolean = false,
    loadingColor: Color,
    iconStart: Painter? = null,
    iconEnd: Painter? = null,
) {
    val textAlpha = if (showLoading) 0f else 1f

    iconStart?.let {
        Image(
            painter = it,
            modifier = Modifier.padding(end = 12.dp).size(24.dp),
            contentDescription = null,
        )
    }

    Box(
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(textPadding).alpha(textAlpha),
        )

        if (showLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp,
                color = loadingColor,
            )
        }
    }

    iconEnd?.let {
        Image(
            painter = it,
            modifier = Modifier.padding(start = 12.dp).size(24.dp),
            contentDescription = null,
        )
    }
}

// endregion
