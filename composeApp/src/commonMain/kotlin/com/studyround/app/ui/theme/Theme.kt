package com.studyround.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

/**
 * Dark Mode Composition Local for StudyRoundTheme.
 */
private val LocalIsDarkMode = compositionLocalOf<Boolean> {
    error("LocalIsDarkMode CompositionLocal not set")
}

@Composable
fun StudyRoundTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalIsDarkMode provides darkTheme,
    ) {
        val colors = when {
            darkTheme -> MaterialDarkColors
            else -> MaterialLightColors
        }

        MaterialTheme(
            colors = colors,
            typography = MaterialTypography,
            content = content,
        )
    }
}

object StudyRoundTheme {
    val colors: Colors
        @Composable
        get() = StudyRoundColors.getColors(LocalIsDarkMode.current)

    val typography: Typography
        @Composable
        get() = StudyRoundTypography(Fonts.quicksand, Fonts.montserrat)

    val darkMode: Boolean
        @Composable
        get() = LocalIsDarkMode.current
}

//    val view = getPlatformContext()
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }