package com.studyround.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import com.studyround.app.storage.AppPreferences
import com.studyround.app.ui.main.RootScreen
import com.studyround.app.ui.theme.StudyRoundTheme
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App(prefs: AppPreferences = koinInject()) {
    KoinContext {
        var darkMode by remember { mutableStateOf(prefs.darkMode) }
        val darkModeState by rememberUpdatedState(darkMode)

        StudyRoundTheme(darkTheme = darkModeState ?: isSystemInDarkTheme()) {
            Surface {
                Navigator(RootScreen())
            }
        }

        LaunchedEffect(Unit) {
            prefs.observeDarkMode().collect { darkMode = it }
        }
    }
}
