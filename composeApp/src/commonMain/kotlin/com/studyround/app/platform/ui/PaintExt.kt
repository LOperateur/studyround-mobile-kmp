package com.studyround.app.platform.ui

import androidx.compose.ui.graphics.Paint

expect fun Paint.setNativeBlurMaskFilter(blurRadius: Float)

expect fun Paint.setNativeStrokePaintStyleMode()

expect fun Paint.setNativeIsAntiAlias(isAntiAlias: Boolean)

expect fun Paint.setNativeIsDither(isDither: Boolean)

expect fun Paint.setNativeColor(color: Int)

expect fun Paint.setNativeStrokeWidth(strokeWidth: Float)
