package com.studyround.app.platform.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

@Composable
actual fun SystemBarColors(statusBarColor: Color, navBarColor: Color) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        SideEffect {
            window.statusBarColor = statusBarColor.toArgb()
            window.navigationBarColor = navBarColor.toArgb()
        }
    }
}

@Composable
actual fun DynamicSystemBarColors(
    dynamicStatusBarColor: Color,
    dynamicNavBarColor: Color,
    statusBarColor: Color,
    navBarColor: Color,
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window

        SideEffect {
            window.statusBarColor = dynamicStatusBarColor.toArgb()
            window.navigationBarColor = dynamicNavBarColor.toArgb()
        }

        DisposableEffect(Unit) {
            onDispose {
                window.statusBarColor = statusBarColor.toArgb()
                window.navigationBarColor = navBarColor.toArgb()
            }
        }
    }
}
