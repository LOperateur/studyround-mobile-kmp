package com.studyround.app.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class RootScreen : Screen {

    @Composable
    override fun Content() {
        Box(modifier = Modifier.fillMaxSize()) { }
    }
}
