package com.studyround.app.platform.ui

import android.graphics.BlurMaskFilter
import androidx.compose.ui.graphics.Paint

actual fun Paint.setBlurMaskFilter(blurRadius: Float) {
    this.asFrameworkPaint().maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
}

actual fun Paint.setStrokePaintStyleMode() {
    this.asFrameworkPaint().style = android.graphics.Paint.Style.STROKE
}
