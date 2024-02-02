package com.studyround.app.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.studyround.app.MR
import com.studyround.app.ui.composables.alert.AlertBannerView
import com.studyround.app.ui.composables.alert.AlertBannerViewModel
import com.studyround.app.ui.composables.alert.AlertManager
import com.studyround.app.ui.composables.alert.LocalAlertManager
import com.studyround.app.ui.composables.transitions.RootScreenSplashTransition
import com.studyround.app.ui.features.splash.SplashScreen
import com.studyround.app.ui.navigation.navigate
import dev.icerock.moko.resources.compose.stringResource

class RootScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<RootViewModel>()

        val alertBannerViewModel = getScreenModel<AlertBannerViewModel>()
        val alertManager = AlertManager(alertBannerViewModel::processEvent)

        val viewState by viewModel.viewState.collectAsState()

        // Navigator for the children of the RootScreen
        var rootNavigator by remember { mutableStateOf<Navigator?>(null) }

        // Used to monitor the splash screen to control the provided transition
        val splashMonitor by remember { mutableStateOf(SplashMonitor(true)) }

        CompositionLocalProvider(LocalAlertManager provides alertManager) {
            Navigator(screen = SplashScreen { splashMonitor.isSplashScreenShowing = false }) {
                rootNavigator = it
                RootScreenSplashTransition(it, splashMonitor)
            }
        }

        AlertBannerView(alertBannerViewModel)

        if (!viewState.showForceUpgradeScreen) {
            LaunchedEffect(viewModel.viewEffects) {
                viewModel.viewEffects.collect { effect ->
                    when (effect) {
                        is Navigate -> {
                            rootNavigator.navigate(
                                RootRouteMap(effect.destination).getScreen(),
                                effect.replace,
                            )
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = stringResource(MR.strings.force_update_prompt))
            }
        }
    }

    data class SplashMonitor(var isSplashScreenShowing: Boolean)
}
