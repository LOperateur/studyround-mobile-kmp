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
    background = StudyRoundColors.Light.tone1,
    surface = StudyRoundColors.Light.tone1,
    onPrimary = StudyRoundColors.Light.tone4, // Text
    onSecondary = StudyRoundColors.Light.tone4, // Text
    onBackground = StudyRoundColors.Light.tone4, // Text
    onSurface = StudyRoundColors.Light.tone4, // Text
)

val MaterialDarkColors = darkColors(
    primary = StudyRoundColors.Dark.primary,
    secondary = StudyRoundColors.Dark.secondary,
    background = StudyRoundColors.Dark.primary1,
    surface = StudyRoundColors.Dark.primary1,
    onPrimary = StudyRoundColors.Dark.tone5, // Text
    onSecondary = StudyRoundColors.Dark.tone5, // Text
    onBackground = StudyRoundColors.Dark.tone5, // Text
    onSurface = StudyRoundColors.Dark.tone5, // Text
)

@Suppress("PropertyName")
interface Colors {
    // Absolute colours
    val white: Color
    val black: Color
    val gray: Color
    val shadow: Color

    // Variable colours
    val primary: Color
    val secondary: Color
    val tertiary: Color
    val danger: Color
    val success: Color

    val tone0: Color
    val tone1: Color
    val tone2: Color
    val tone3: Color
    val tone4: Color
    val tone5: Color
    val tone6: Color

    val primary0: Color
    val primary1: Color
    val primary2: Color
    val primary3: Color
    val primary4: Color

    val secondary0: Color
    val secondary1: Color
    val secondary2: Color
    val secondary3: Color
    val secondary4: Color

    val tertiary0: Color
    val tertiary1: Color

    val deviation_primary1_white: Color // Icons, Contrast Text
    val deviation_tone4_tone5: Color // Text
    val deviation_primary1_primary4: Color // Link Text
    val deviation_primary0_primary4: Color // Bright accent Text
    val deviation_tone1_primary1: Color // Main Background
    val deviation_primary2_primary0: Color // Green Background
    val deviation_white_primary0: Color // TextField Background, App Top Bar
    val deviation_primary3_primary0: Color // System bars
    val deviation_primary3_primary1: Color // Splash
    val deviation_white_tone5: Color // 'White' Surface
    val deviation_secondary1_secondary3: Color // Default Secondary colour
    val deviation_white_dullWhite: Color

    // Primary button deviations
    val button_deviation_primary1_primary2: Color
    val button_deviation_primary2_primary3: Color
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
        override val white = Color(0xFF_FFFFFF)
        override val black = Color(0xFF_000F10)
        override val gray = Color(0xFF_757575)
        override val shadow = Color.Black.copy(alpha = 0.3f)

        override val primary = Color(0xFF_018184)
        override val secondary = Color(0xFF_FF8051)
        override val tertiary = Color(0xFF_FFD45D)
        override val danger = Color(0xFF_E53E3E)
        override val success = Color(0xFF_179848)

        override val tone0 = white
        override val tone1 = Color(0xFF_F5F5F5)
        override val tone2 = Color(0xFF_C7C7C7)
        override val tone3 = gray
        override val tone4 = Color(0xFF_383838)
        override val tone5 = Color(0xFF_0A0A0A)
        override val tone6 = black

        override val primary0 = Color(0xFF_50EAEC)
        override val primary1 = Color(0xFF_018184) // Primary
        override val primary2 = Color(0xFF_055F5B)
        override val primary3 = Color(0xFF_002E2F)
        override val primary4 = Color(0xFF_001B1C)

        override val secondary0 = Color(0xFF_FFA483)
        override val secondary1 = Color(0xFF_FF8051) // Secondary
        override val secondary2 = Color(0xFF_F06C3F)
        override val secondary3 = Color(0xFF_E1582D)
        override val secondary4 = Color(0xFF_AA4221)

        override val tertiary0 = Color(0xFF_FFD45D) // Tertiary
        override val tertiary1 = Color(0xFF_FFB300)

        override val deviation_primary1_white = primary1
        override val deviation_tone4_tone5 = tone4
        override val deviation_primary1_primary4 = primary1
        override val deviation_primary0_primary4 = primary0
        override val deviation_tone1_primary1 = tone1
        override val deviation_primary2_primary0 = primary2
        override val deviation_white_primary0 = white
        override val deviation_primary3_primary0 = primary3
        override val deviation_primary3_primary1 = primary3
        override val deviation_white_tone5 = white
        override val deviation_secondary1_secondary3 = secondary1
        override val deviation_white_dullWhite = white

        override val button_deviation_primary1_primary2 = primary1
        override val button_deviation_primary2_primary3 = primary2
    }

    @Immutable
    object Dark : Colors {
        override val white = Color(0xFF_FFFFFF)
        override val black = Color(0xFF_000F10)
        override val gray = Color(0xFF_757575)
        override val shadow = Color.Unspecified // No shadow in dark mode

        override val primary = Color(0xFF_002E2F)
        override val secondary = Color(0xFF_E1582D)
        override val tertiary = Color(0xFF_FFB300)
        override val danger = Color(0xFF_FFB3B3)
        override val success = Color(0xFF_B9F5D0)

        override val tone0 = black
        override val tone1 = Color(0xFF_0A0A0A)
        override val tone2 = Color(0xFF_383838)
        override val tone3 = gray
        override val tone4 = Color(0xFF_C7C7C7)
        override val tone5 = Color(0xFF_F5F5F5)
        override val tone6 = white

        override val primary0 = Color(0xFF_001B1C)
        override val primary1 = Color(0xFF_002E2F) // Primary
        override val primary2 = Color(0xFF_055F5B)
        override val primary3 = Color(0xFF_018184)
        override val primary4 = Color(0xFF_50EAEC)

        override val secondary0 = Color(0xFF_AA4221)
        override val secondary1 = Color(0xFF_E1582D) // Secondary
        override val secondary2 = Color(0xFF_F06C3F)
        override val secondary3 = Color(0xFF_FF8051)
        override val secondary4 = Color(0xFF_FFA483)

        override val tertiary0 = Color(0xFF_FFB300) // Tertiary
        override val tertiary1 = Color(0xFF_FFD45D)

        override val deviation_primary1_white = white
        override val deviation_tone4_tone5 = tone5
        override val deviation_primary1_primary4 = primary4
        override val deviation_primary0_primary4 = primary4
        override val deviation_tone1_primary1 = primary1
        override val deviation_primary2_primary0 = primary0
        override val deviation_white_primary0 = primary0
        override val deviation_primary3_primary0 = primary0
        override val deviation_primary3_primary1 = primary1
        override val deviation_white_tone5 = tone5
        override val deviation_secondary1_secondary3 = secondary3
        override val deviation_white_dullWhite = white.copy(alpha = 0.6f)

        override val button_deviation_primary1_primary2 = primary2
        override val button_deviation_primary2_primary3 = primary3
    }
}
