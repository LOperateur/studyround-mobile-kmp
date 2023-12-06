package com.studyround.app.platform.utils

import androidx.compose.runtime.Composable
import com.russhwolf.settings.Settings

typealias Credentials = Settings

expect class PlatformContext

@Composable
expect fun getPlatformContext(): PlatformContext
