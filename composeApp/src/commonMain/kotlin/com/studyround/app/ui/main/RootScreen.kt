package com.studyround.app.ui.main

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import cafe.adriel.voyager.transitions.SlideTransition
import com.studyround.app.MR
import com.studyround.app.ui.composables.transitions.FadeInTransition
import com.studyround.app.ui.features.splash.SplashScreen
import com.studyround.app.ui.navigation.navigate
import dev.icerock.moko.resources.compose.stringResource

class RootScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<RootViewModel>()
        val viewState by viewModel.viewState.collectAsState()
        var rootNavigator by remember { mutableStateOf<Navigator?>(null) }

        Navigator(screen = SplashScreen()) {
            rootNavigator = it
            if (it.canPop)
                SlideTransition(it)
            else
                FadeInTransition(it, animationSpec = tween())
        }

        if (!viewState.showForceUpgradeScreen) {
            LaunchedEffect(viewModel.viewEffects) {
                viewModel.viewEffects.collect { effect ->
                    when (effect) {
                        is Navigate -> {
                            rootNavigator.navigate(RootRouteMap(effect.destination).getScreen())
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
}
