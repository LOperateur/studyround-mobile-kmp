package com.studyround.app.ui.composables.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.rememberImageSuccessPainter
import com.seiko.imageloader.ui.AutoSizeBox
import com.studyround.app.ui.theme.StudyRoundTheme

@Composable
fun RemoteImage(
    url: String,
    modifier: Modifier = Modifier,
    placeholderPainter: Painter? = null,
    placeholderTint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    onLoading: (@Composable () -> Unit)? = { DefaultLoader() },
) {
    AutoSizeBox(
        url = url,
        modifier = modifier,
    ) { action ->
        when (action) {
            is ImageAction.Success -> {
                Image(
                    rememberImageSuccessPainter(action),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Avatar",
                )
            }

            is ImageAction.Loading -> {
                placeholderPainter?.let {
                    Icon(
                        placeholderPainter,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "Placeholder",
                        tint = placeholderTint,
                    )
                }

                onLoading?.invoke()
            }

            is ImageAction.Failure -> {
                placeholderPainter?.let {
                    Icon(
                        placeholderPainter,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "Placeholder",
                        tint = placeholderTint,
                    )
                }
            }
        }
    }
}

@Composable
private fun DefaultLoader() {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        color = StudyRoundTheme.colors.white,
        strokeWidth = 2.dp,
    )
}
