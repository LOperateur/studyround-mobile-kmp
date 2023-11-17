package com.studyround.app

import androidx.compose.runtime.Composable
import com.studyround.app.platform.AndroidBridge

actual fun getPlatformName(): String = "Android"

@Composable
fun MainView() = App(AndroidBridge())
