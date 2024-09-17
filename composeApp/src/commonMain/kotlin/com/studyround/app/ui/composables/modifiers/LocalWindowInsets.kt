package com.studyround.app.ui.composables.modifiers

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// TODO: These have the correct window insets as a workaround for this voyager bug:
//  https://github.com/adrielcafe/voyager/issues/484
val LocalSystemBarsWindowInsets = compositionLocalOf { WindowInsets(0.dp, 0.dp, 0.dp, 0.dp) }
val LocalStatusBarsWindowInsets = compositionLocalOf { WindowInsets(0.dp, 0.dp, 0.dp, 0.dp) }
val LocalNavigationBarsWindowInsets = compositionLocalOf { WindowInsets(0.dp, 0.dp, 0.dp, 0.dp) }

@Composable
fun provideLocalWindowInsets(): Array<ProvidedValue<*>> {
    return arrayOf(
        LocalSystemBarsWindowInsets provides WindowInsets.systemBars,
        LocalStatusBarsWindowInsets provides WindowInsets.statusBars,
        LocalNavigationBarsWindowInsets provides WindowInsets.navigationBars,
    )
}

@Composable
fun Modifier.localSystemBarsPadding() = windowInsetsPadding(LocalSystemBarsWindowInsets.current)

@Composable
fun Modifier.localStatusBarsPadding() = windowInsetsPadding(LocalStatusBarsWindowInsets.current)

@Composable
fun Modifier.localNavigationBarsPadding() = windowInsetsPadding(LocalNavigationBarsWindowInsets.current)
