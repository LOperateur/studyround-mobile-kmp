package com.studyround.app.platform.ui

import android.graphics.BlurMaskFilter
import androidx.compose.ui.graphics.Paint

actual fun Paint.setNativeBlurMaskFilter(blurRadius: Float) {
    this.asFrameworkPaint().maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
}

actual fun Paint.setNativeStrokePaintStyleMode() {
    this.asFrameworkPaint().style = android.graphics.Paint.Style.STROKE
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
