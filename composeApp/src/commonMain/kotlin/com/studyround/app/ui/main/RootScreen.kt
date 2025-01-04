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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.seiko.imageloader.LocalImageLoader
import com.studyround.app.platform.utils.StudyRoundImageLoader
import com.studyround.app.ui.composables.alert.AlertBannerView
import com.studyround.app.ui.composables.alert.AlertBannerViewModel
import com.studyround.app.ui.composables.alert.AlertManager
import com.studyround.app.ui.composables.alert.LocalAlertManager
import com.studyround.app.ui.composables.modifiers.provideLocalWindowInsets
import com.studyround.app.ui.composables.transitions.rootScreenPopEnterTransition
import com.studyround.app.ui.composables.transitions.rootScreenPopExitTransition
import com.studyround.app.ui.composables.transitions.rootScreenSplashEnterTransition
import com.studyround.app.ui.composables.transitions.rootScreenSplashExitTransition
import com.studyround.app.ui.features.auth.AuthScreen
import com.studyround.app.ui.features.main.MainScreen
import com.studyround.app.ui.features.onboarding.OnboardingCarouselScreen
import com.studyround.app.ui.features.splash.SplashScreen
import com.studyround.app.ui.features.survey.RegSurveyScreen
import com.studyround.app.ui.navigation.navigateToRoute
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.force_update_prompt

@Composable
fun RootScreen() {
    val viewModel = koinViewModel<RootViewModel>()

    val alertBannerViewModel = koinViewModel<AlertBannerViewModel>()
    val alertManager = AlertManager(alertBannerViewModel::processEvent)

    val viewState by viewModel.viewState.collectAsState()

    val imageLoader = koinInject<StudyRoundImageLoader>()

    // Nav controller for the children of RootScreen's NavHost
    val rootNavController: NavHostController = rememberNavController()

    // Used to monitor the splash screen to control the provided transition
    val splashMonitor by remember { mutableStateOf(SplashMonitor(true)) }

    CompositionLocalProvider(
        LocalAlertManager provides alertManager,
        LocalImageLoader provides remember { imageLoader.generateImageLoader() },
        *provideLocalWindowInsets(),
    ) {
        NavHost(
            navController = rootNavController,
            startDestination = RootDestination.Splash,
            modifier = Modifier.fillMaxSize(),
            enterTransition = { rootScreenSplashEnterTransition(splashMonitor) },
            exitTransition = { rootScreenSplashExitTransition(splashMonitor) },
            popEnterTransition = { rootScreenPopEnterTransition },
            popExitTransition = { rootScreenPopExitTransition },
        ) {
            composable<RootDestination.Splash> {
                SplashScreen(
                    onSplashRemoved = { splashMonitor.isSplashScreenShowing = false },
                )
            }
            composable<RootDestination.Onboarding> {
                OnboardingCarouselScreen()
            }
            composable<RootDestination.Auth> {
                AuthScreen()
            }
            composable<RootDestination.Main> {
                MainScreen()
            }
            composable<RootDestination.RegSurvey> {
                RegSurveyScreen(
                    onNavigateToMainSection = {
                        rootNavController.navigateToRoute(
                            RootDestination.Main,
                            true,
                        )
                    }
                )
            }
        }
    }

    AlertBannerView(alertBannerViewModel)

    if (!viewState.showForceUpgradeScreen) {
        LaunchedEffect(viewModel.viewEffects) {
            viewModel.viewEffects.collect { effect ->
                when (effect) {
                    is Navigate -> {
                        rootNavController.navigateToRoute(effect.destination, effect.replace)
                    }
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(Res.string.force_update_prompt))
        }
    }
}

data class SplashMonitor(var isSplashScreenShowing: Boolean)
