package com.studyround.app

import androidx.compose.ui.window.ComposeUIViewController
import com.studyround.app.platform.IosPlatformBridge

actual fun getPlatformName(): String = "iOS"

fun MainViewController(iosBridge: IosPlatformBridge) = ComposeUIViewController { App(iosBridge) }