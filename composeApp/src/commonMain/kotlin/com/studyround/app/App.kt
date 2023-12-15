package com.studyround.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.studyround.app.storage.AppPreferences
import com.studyround.app.ui.main.RootScreen
import com.studyround.app.ui.theme.StudyRoundTheme
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App(prefs: AppPreferences = koinInject()) {
    KoinContext {
        StudyRoundTheme(darkTheme = prefs.darkMode ?: isSystemInDarkTheme()) {
            Surface {
                Navigator(RootScreen())
            }
        }
    }
}
