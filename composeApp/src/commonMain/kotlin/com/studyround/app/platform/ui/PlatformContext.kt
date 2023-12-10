package com.studyround.app.platform.ui

import androidx.compose.runtime.Composable

expect class PlatformContext

@Composable
expect fun getPlatformContext(): PlatformContext
