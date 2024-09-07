package com.studyround.app.platform.ui

import androidx.compose.ui.graphics.Paint
import org.jetbrains.skia.FilterBlurMode
import org.jetbrains.skia.MaskFilter
import org.jetbrains.skia.PaintMode

actual fun Paint.setNativeBlurMaskFilter(blurRadius: Float) {
    this.asFrameworkPaint().maskFilter = MaskFilter.makeBlur(FilterBlurMode.NORMAL, blurRadius / 2)
}

actual fun Paint.setNativeStrokePaintStyleMode() {
    this.asFrameworkPaint().mode = PaintMode.STROKE
}

actual fun Paint.setNativeIsAntiAlias(isAntiAlias: Boolean) {
    this.asFrameworkPaint().isAntiAlias = isAntiAlias
}

actual fun Paint.setNativeIsDither(isDither: Boolean) {
    this.asFrameworkPaint().isDither = isDither
}

actual fun Paint.setNativeColor(color: Int) {
    this.asFrameworkPaint().color = color
}

actual fun Paint.setNativeStrokeWidth(strokeWidth: Float) {
    this.asFrameworkPaint().strokeWidth = strokeWidth
}
