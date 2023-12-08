package com.studyround.app.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val MaterialTypography = androidx.compose.material.Typography(
    defaultFontFamily = StudyRoundTypography.defaultFont,
    h1 = StudyRoundTypography.titleLarge,
    h2 = StudyRoundTypography.titleLarge,
    h3 = StudyRoundTypography.titleMedium,
    h4 = StudyRoundTypography.titleMedium,
    h5 = StudyRoundTypography.titleSmall,
    h6 = StudyRoundTypography.titleSmall,
    subtitle1 = StudyRoundTypography.titleSmall,
    subtitle2 = StudyRoundTypography.titleSmall,
    body1 = StudyRoundTypography.bodyMedium,
    body2 = StudyRoundTypography.bodySmall,
    button = StudyRoundTypography.bodySmall,
    caption = StudyRoundTypography.labelMedium,
    overline = StudyRoundTypography.labelMedium,
)

interface Typography {
    val defaultFont: FontFamily
    val titleLarge: TextStyle
    val titleMedium: TextStyle
    val titleSmall: TextStyle
    val bodyLarge: TextStyle
    val bodyMedium: TextStyle
    val bodySmall: TextStyle
    val labelLarge: TextStyle
    val labelMedium: TextStyle
    val labelSmall: TextStyle
}

internal object StudyRoundTypography : Typography {
    override val defaultFont = FontFamily(
        Font(
            resId = 0,
            weight = FontWeight.Normal,
            style = FontStyle.Normal,
        ),
        Font(
            resId = 0,
            weight = FontWeight.Bold,
            style = FontStyle.Normal,
        )
    )

    private val baseStyle = TextStyle(
        fontFamily = defaultFont,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
    )

    // Title
    override val titleLarge = baseStyle.copy(
        fontSize = 48.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.Bold,
    )

    override val titleMedium = baseStyle.copy(
        fontSize = 32.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.Bold,
    )

    override val titleSmall = baseStyle.copy(
        fontSize = 24.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Bold,
    )

    // Body
    override val bodyLarge = baseStyle.copy(
        fontSize = 32.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.SemiBold,
    )

    override val bodyMedium = baseStyle.copy(
        fontSize = 24.sp,
        lineHeight = 28.sp,
    )

    override val bodySmall = baseStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
    )

    // Label
    override val labelLarge = baseStyle.copy(
        fontSize = 24.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.SemiBold,
    )

    override val labelMedium = baseStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
    )

    override val labelSmall = baseStyle.copy(
        fontSize = 12.sp,
        lineHeight = 16.sp,
    )
}
