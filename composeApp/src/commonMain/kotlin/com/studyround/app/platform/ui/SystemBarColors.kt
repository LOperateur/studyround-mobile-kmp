package com.studyround.app.platform.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
expect fun SystemBarColors(statusBarColor: Color, navBarColor: Color)

@Composable
expect fun DynamicSystemBarColors(
    dynamicStatusBarColor: Color,
    dynamicNavBarColor: Color,
    statusBarColor: Color,
    navBarColor: Color,
)
