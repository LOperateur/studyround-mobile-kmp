package com.studyround.app.ui.utils

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

fun WindowSizeClass.isTabletLandscapeMode(): Boolean {
    return this.widthSizeClass == WindowWidthSizeClass.Expanded && this.heightSizeClass >= WindowHeightSizeClass.Medium
}

fun WindowSizeClass.isAtLeastMediumWidth(): Boolean {
    return this.widthSizeClass >= WindowWidthSizeClass.Medium
}
