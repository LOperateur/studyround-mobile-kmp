package com.studyround.app.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

// Set Material Colors
val MaterialLightColors = lightColors(
    primary = StudyRoundColors.Light.primary,
    secondary = StudyRoundColors.Light.secondary,
)

val MaterialDarkColors = darkColors(
    primary = StudyRoundColors.Dark.primary,
    secondary = StudyRoundColors.Dark.secondary,
)

interface Colors {
    val primary: Color
    val secondary: Color
}

internal object StudyRoundColors {
    @Composable
    fun getColors(
        darkMode: Boolean
    ) = if (darkMode) {
        Dark
    } else {
        Light
    }

    @Immutable
    object Light : Colors {
        override val primary = Color(0xFF_FFFFFF)
        override val secondary = Color(0xFF_FFFFFF)
    }

    @Immutable
    object Dark : Colors {
        override val primary = Color(0xFF_000000)
        override val secondary = Color(0xFF_000000)
    }
}
