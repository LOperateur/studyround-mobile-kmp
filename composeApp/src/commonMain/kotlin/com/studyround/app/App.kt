package com.studyround.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.jetpack.ProvideNavigatorLifecycleKMPSupport
import com.studyround.app.data.storage.preferences.AppPreferences
import com.studyround.app.ui.main.RootScreen
import com.studyround.app.ui.theme.StudyRoundTheme
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App(prefs: AppPreferences = koinInject()) {
    KoinContext {
        var darkMode by remember { mutableStateOf(prefs.darkMode) }

        StudyRoundTheme(darkTheme = darkMode ?: isSystemInDarkTheme()) {
            Surface {
                ProvideNavigatorLifecycleKMPSupport {
                    RootScreen()
                }
            }
        }

        LaunchedEffect(Unit) {
            prefs.observeDarkMode().collect { darkMode = it }
        }
    }
}
