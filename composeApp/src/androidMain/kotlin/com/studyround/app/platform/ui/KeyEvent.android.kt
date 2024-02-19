package com.studyround.app.platform.ui

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key

actual fun KeyEvent.isBackspaceKeyEvent(): Boolean = key == Key.Backspace
