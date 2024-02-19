package com.studyround.app.ui.utils

import androidx.compose.runtime.Composable
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.DrawableResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.icon_watermark
import studyround.composeapp.generated.resources.icon_watermark_night

@Composable
fun DrawableResource.themed(isDarkMode: Boolean = StudyRoundTheme.darkMode): DrawableResource {
    val themedMap = mapOf(
        Res.drawable.icon_watermark to Res.drawable.icon_watermark_night,
    )

    return if (isDarkMode) {
        themedMap[this] ?: this
    } else {
        this
    }
}
