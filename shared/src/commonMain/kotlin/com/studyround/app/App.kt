package com.studyround.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.studyround.app.platform.SharedPlatform
import com.studyround.app.platform.components.SampleComponent
import com.studyround.app.platform.utils.NetworkListener
import com.studyround.app.platform.utils.NetworkStatus
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(
    platform: SharedPlatform = koinInject(),
    sample: SampleComponent = koinInject(),
    networkListener: NetworkListener = koinInject(),
) {
    KoinContext {
        MaterialTheme {
            var greetingText by remember { mutableStateOf(sample.returnGreeting("Mofe")) }
            var showImage by remember { mutableStateOf(false) }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    greetingText = "Hello, ${platform.deviceName}"
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
            val networkStatus by networkListener.networkStatus.collectAsState(NetworkStatus.Connected)
            Text(text = networkStatus.toString())
        }
    }
}
