package com.studyround.app.platform.ui

import androidx.compose.ui.text.PlatformTextStyle

/**
 * TODO: Remove by compose 1.6.0 when "includeFontPadding"
 *  defaults to 'false' on Android.
 */
actual fun getPlatformTextStyle() = PlatformTextStyle(null, null)
