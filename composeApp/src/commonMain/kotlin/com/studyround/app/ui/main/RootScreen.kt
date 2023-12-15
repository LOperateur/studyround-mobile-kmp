package com.studyround.app.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.studyround.app.MR
import dev.icerock.moko.resources.compose.stringResource

class RootScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<RootViewModel>()
        val viewState by viewModel.viewState.collectAsState()

        if (!viewState.showForceUpgradeScreen) {
            val navigator = LocalNavigator.currentOrThrow

            LaunchedEffect(viewModel.viewEffects) {
                viewModel.viewEffects.collect { effect ->
                    when (effect) {
                        is Navigate -> {
                            navigator.replace(RootRouteMap(effect.destination).getScreen())
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
