package com.studyround.app.ui.composables.modifiers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.neumorphic.LightSource
import com.studyround.app.ui.neumorphic.neumorphic
import com.studyround.app.ui.neumorphic.shape.Flat
import com.studyround.app.ui.neumorphic.shape.Oval
import com.studyround.app.ui.neumorphic.shape.RoundedCorner
import com.studyround.app.ui.theme.StudyRoundTheme

@Composable
fun Modifier.neuSurface(
    darkMode: Boolean = StudyRoundTheme.darkMode,
    cornerRadius: Dp? = null,
): Modifier {
    val cornerShape = if (cornerRadius == null) Oval else RoundedCorner(cornerRadius)

    return if (darkMode) {
        neumorphic(
            lightShadowColor = StudyRoundTheme.colors.tone5.copy(alpha = 0.1f),
            darkShadowColor = StudyRoundTheme.colors.tone1.copy(alpha = 0.4f),
            lightSource = LightSource.LEFT_TOP,
            shadowElevation = 6.dp,
            shape = Flat(cornerShape = cornerShape),
        )
    } else {
        neumorphic(
            lightShadowColor = StudyRoundTheme.colors.white,
            darkShadowColor = StudyRoundTheme.colors.tone5.copy(alpha = 0.1f),
            lightSource = LightSource.LEFT_TOP,
            shadowElevation = 6.dp,
            shape = Flat(cornerShape = cornerShape),
        )
    }
}
