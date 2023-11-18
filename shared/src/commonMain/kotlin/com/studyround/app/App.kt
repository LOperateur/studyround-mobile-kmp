package com.studyround.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import co.touchlab.kermit.Logger
import com.studyround.app.platform.PlatformComponents
import com.studyround.app.platform.components.SampleComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(platform: PlatformComponents = koinInject()) {

    KoinContext {
        SampleComposable()
        MaterialTheme {
            var greetingText by remember { mutableStateOf("Hello, World!") }
            var showImage by remember { mutableStateOf(false) }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    greetingText = "Hello, ${platform.version}"
                    showImage = !showImage
                }) {
                    Text(greetingText)
                }
                AnimatedVisibility(showImage) {
                    Image(
                        painterResource("compose-multiplatform.xml"),
                        null
                    )
                }
            }
        }
    }
}

@Composable
private fun SampleComposable(
    component: SampleComponent = koinInject(),
    platform: PlatformComponents = koinInject(),
) {
    LaunchedEffect(Unit) {
        component.sayHello(platform.version)
        Logger.d(tag = "Sample", messageString = component.returnGreeting("StudyRound"))
    }
}
