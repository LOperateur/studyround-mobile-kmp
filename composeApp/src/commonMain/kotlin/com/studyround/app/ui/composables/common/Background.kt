package com.studyround.app.ui.composables.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.studyround.app.ui.theme.StudyRoundTheme
import com.studyround.app.ui.utils.themed
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import org.jetbrains.compose.resources.painterResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.icon_watermark

@Composable
fun StudyRoundBackground(
    modifier: Modifier = Modifier,
    darkMode: Boolean = StudyRoundTheme.darkMode,
) {
    KamelImage(
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        resource = Resource.Success(painterResource(Res.drawable.icon_watermark.themed(darkMode))),
        contentDescription = ""
    )
}
