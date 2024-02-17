package com.studyround.app.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.studyround.app.platform.ui.getPlatformTextStyle
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import studyround.composeapp.generated.resources.Res

// Set of Material typography styles to start with
val MaterialTypography
    @Composable get() = with(StudyRoundTypography(Fonts.quicksand, Fonts.montserrat)) {
        androidx.compose.material.Typography(
            defaultFontFamily = Fonts.quicksand,
            h1 = titleLarge,
            h2 = titleLarge,
            h3 = titleMedium,
            h4 = titleMedium,
            h5 = titleSmall,
            h6 = titleSmall,
            subtitle1 = titleSmall,
            subtitle2 = titleSmall,
            body1 = bodyMedium,
            body2 = bodySmall,
            button = bodySmall,
            caption = labelMedium,
            overline = labelMedium,
        )
    }

interface Typography {
    val quicksandFont: FontFamily
    val montserratFont: FontFamily

    val titleLarge: TextStyle
    val titleMedium: TextStyle
    val titleSmall: TextStyle
    val titleExtraSmall: TextStyle
    val bodyLarge: TextStyle
    val bodyMedium: TextStyle
    val bodySmall: TextStyle
    val labelLarge: TextStyle
    val labelMedium: TextStyle
    val labelSmall: TextStyle
}

internal class StudyRoundTypography(
    override val quicksandFont: FontFamily,
    override val montserratFont: FontFamily,
) : Typography {

    private val baseStyle = TextStyle(
        fontFamily = quicksandFont,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        platformStyle = getPlatformTextStyle(),
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

    override val titleExtraSmall = baseStyle.copy(
        fontSize = 20.sp,
        lineHeight = 24.sp,
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
        fontWeight = FontWeight.SemiBold,
    )

    override val bodySmall = baseStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.SemiBold,
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

internal object Fonts {
    @OptIn(ExperimentalResourceApi::class)
    val quicksand
        @Composable get() = FontFamily(
            Font(
                resource = Res.font.quicksand_regular,
                weight = FontWeight.Normal,
                style = FontStyle.Normal,
            ),
            Font(
                resource = Res.font.quicksand_semibold,
                weight = FontWeight.SemiBold,
                style = FontStyle.Normal,
            ),
            Font(
                resource = Res.font.quicksand_bold,
                weight = FontWeight.Bold,
                style = FontStyle.Normal,
            )
        )

    @OptIn(ExperimentalResourceApi::class)
    val montserrat
        @Composable get() = FontFamily(
            Font(
                resource = Res.font.montserrat_regular,
                weight = FontWeight.Normal,
                style = FontStyle.Normal,
            ),
            Font(
                resource = Res.font.montserrat_semibold,
                weight = FontWeight.SemiBold,
                style = FontStyle.Normal,
            ),
            Font(
                resource = Res.font.montserrat_bold,
                weight = FontWeight.Bold,
                style = FontStyle.Normal,
            )
        )
}
