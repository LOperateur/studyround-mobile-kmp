package com.studyround.app.platform.ui

import androidx.compose.ui.graphics.Paint
import org.jetbrains.skia.FilterBlurMode
import org.jetbrains.skia.MaskFilter
import org.jetbrains.skia.PaintMode

actual fun Paint.setBlurMaskFilter(blurRadius: Float) {
    this.asFrameworkPaint().maskFilter = MaskFilter.makeBlur(FilterBlurMode.NORMAL, blurRadius / 2)
}

actual fun Paint.setStrokePaintStyleMode() {
    this.asFrameworkPaint().mode = PaintMode.STROKE
}
