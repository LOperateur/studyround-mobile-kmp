package com.studyround.app.ui.features.dashboard.courses.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.composables.alert.LocalAlertManager
import com.studyround.app.ui.composables.buttons.SecondaryButton
import com.studyround.app.ui.composables.common.RemoteImage
import com.studyround.app.ui.composables.common.StudyRoundBackground
import com.studyround.app.ui.theme.StudyRoundTheme
import com.studyround.app.ui.utils.isAtLeastMediumWidth
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun CourseDetailsBottomSheet(onDismiss: () -> Unit) {
    Box {
        val windowSizeClass = calculateWindowSizeClass()

        val isWide = windowSizeClass.isAtLeastMediumWidth()

        StudyRoundBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val vm = koinViewModel<CourseDetailsViewModel>()
            val viewState by vm.viewState.collectAsState()
            val eventProcessor = vm::processEvent

            val alertManager = LocalAlertManager.current

            var buttonHeight by remember { mutableStateOf(0.dp) }
            val density = LocalDensity.current

            Box {
                RemoteImage(
                    url = viewState.course?.imageUrl.orEmpty(),
                    modifier = Modifier
                        .padding(bottom = buttonHeight / 2)
                        .fillMaxWidth()
                        .aspectRatio(ratio = if (isWide) 2f else 1f)
                        .background(color = StudyRoundTheme.colors.deviation_tone4_tone5.copy(alpha = 0.25f)),
                    contentScale = ContentScale.Crop,
                )

                SecondaryButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .requiredWidthIn(min = 160.dp)
                        .onPlaced { with(density) { buttonHeight = it.size.height.toDp() } },
                    text = "Open",
                ) {}
            }

        }
    }
}
