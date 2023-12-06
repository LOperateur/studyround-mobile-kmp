package com.studyround.app.platform.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

actual class PlatformContext(val androidContext: Context)

/**
 * Composable function that returns the PlatformContext for the Android Platform, wrapping a.
 * context object. Unlike the androidContext provided by Koin which is the Application Context,
 * this one is a UI-based context. Necessary for UI tasks like launching another activity.
 */
@Composable
actual fun getPlatformContext(): PlatformContext = PlatformContext(LocalContext.current)
