package com.studyround.app.platform.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.interop.LocalUIViewController
import platform.UIKit.UIViewController

actual class PlatformContext(val rootViewController: UIViewController)

@Composable
actual fun getPlatformContext(): PlatformContext = PlatformContext(LocalUIViewController.current)
