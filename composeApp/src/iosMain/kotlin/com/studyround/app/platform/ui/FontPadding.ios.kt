package com.studyround.app.platform.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * TODO: Remove by compose 1.6.0
 */
actual fun Modifier.fontPadding(): Modifier {
    return this.padding(bottom = 0.dp)
}
