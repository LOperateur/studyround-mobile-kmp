package com.studyround.app.ui.features.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.studyround.app.ui.composables.common.StudyRoundBackground
import com.studyround.app.ui.composables.common.TextLogo

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        StudyRoundBackground()
        Box(modifier = Modifier.fillMaxSize().systemBarsPadding().padding(horizontal = 8.dp)) {
            Column(modifier = Modifier.matchParentSize()) {
                TextLogo()
                LoginFormContent()
            }
        }
    }

    @Composable
    private fun LoginFormContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Login")
        }
    }
}
