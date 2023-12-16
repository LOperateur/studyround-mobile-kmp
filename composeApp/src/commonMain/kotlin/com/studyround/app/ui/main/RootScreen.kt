package com.studyround.app.ui.main

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
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.studyround.app.MR
import com.studyround.app.ui.navigation.PlaceholderScreen
import com.studyround.app.ui.navigation.navigate
import dev.icerock.moko.resources.compose.stringResource

class RootScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<RootViewModel>()
        val viewState by viewModel.viewState.collectAsState()
        var rootNavigator by remember { mutableStateOf<Navigator?>(null) }

        Navigator(screen = PlaceholderScreen()) {
            rootNavigator = it
            if (it.lastItem is PlaceholderScreen)
                CurrentScreen()
            else
                SlideTransition(it)
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
