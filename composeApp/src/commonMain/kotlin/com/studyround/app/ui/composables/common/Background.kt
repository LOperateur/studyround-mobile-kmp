package com.studyround.app.ui.composables.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.studyround.app.ui.theme.StudyRoundTheme
import com.studyround.app.ui.utils.themed
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import org.jetbrains.compose.resources.painterResource
import studyround.composeapp.generated.resources.*

@Composable
fun StudyRoundBackground(
    modifier: Modifier = Modifier,
    darkMode: Boolean = StudyRoundTheme.darkMode,
    showGradientScrim: Boolean = false,
) {
    Box {
        KamelImage(
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            resource = Resource.Success(painterResource(Res.drawable.icon_watermark.themed(darkMode))),
            contentDescription = ""
        )
        if (showGradientScrim) {
            Box(
                modifier = Modifier.matchParentSize().background(
                    brush = Brush.verticalGradient(
                        0.05f to StudyRoundTheme.colors.primary0.copy(alpha = 0.075f),
                        0.15f to Color.Transparent,
                    ),
                )
            )
        }
    }
}
