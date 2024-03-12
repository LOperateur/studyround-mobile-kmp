package com.studyround.app

import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.window.ComposeUIViewController

@OptIn(ExperimentalComposeApi::class)
fun MainViewController() = ComposeUIViewController(
    // TODO: This will be fixed in a future version of Compose Multiplatform
    //  https://github.com/JetBrains/compose-multiplatform/issues/4460
    //  https://www.jetbrains.com/help/kotlin-multiplatform-dev/whats-new-compose-1-6-0.html#separate-platform-views-for-popups-dialogs-and-dropdowns-ios-desktop
    configure = { platformLayers = false }
) { App() }
