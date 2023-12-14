package com.studyround.app.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.studyround.app.MR
import dev.icerock.moko.resources.compose.stringResource

class RootScreen : Screen {
    @Composable
    override fun Content() {
        val rootViewModel = getScreenModel<RootViewModel>()
        val viewState by rootViewModel.viewState.collectAsState()

        RootScreen(viewState)
    }

    @Composable
    private fun RootScreen(viewState: RootViewState) {
        if (!viewState.showForceUpgradeScreen) {
            Box(modifier = Modifier.fillMaxSize()) { }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = stringResource(MR.strings.force_update_prompt))
            }
        }
    }
}
