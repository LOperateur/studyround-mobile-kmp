package com.studyround.app.platform.ui

import androidx.compose.ui.input.key.KeyEvent
import org.jetbrains.skiko.SkikoKey

actual fun KeyEvent.isBackspaceKeyEvent(): Boolean = nativeKeyEvent.key == SkikoKey.KEY_BACKSPACE
