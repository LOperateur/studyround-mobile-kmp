package com.studyround.app.ui.utils

import androidx.compose.runtime.Composable
import com.studyround.app.MR
import com.studyround.app.ui.theme.StudyRoundTheme
import dev.icerock.moko.resources.ImageResource

@Composable
fun ImageResource.themed(isDarkMode: Boolean = StudyRoundTheme.darkMode): ImageResource {
    val themedMap = mapOf(
        MR.images.icon_watermark to MR.images.icon_watermark_night,
    )

    return if (isDarkMode) {
        themedMap[this] ?: this
    } else {
        this
    }
}
