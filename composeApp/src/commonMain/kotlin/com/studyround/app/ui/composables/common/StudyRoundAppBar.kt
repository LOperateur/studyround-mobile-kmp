package com.studyround.app.ui.composables.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.theme.StudyRoundTheme
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import org.jetbrains.compose.resources.painterResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.*

@Composable
fun StudyRoundAppBar(
    title: String,
    textColor: Color = StudyRoundTheme.colors.deviation_tone4_tone5,
    modifier: Modifier = Modifier,
    onBackPressed: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .background(color = StudyRoundTheme.colors.deviation_white_primary0)
            .shadow(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = StudyRoundTheme.colors.deviation_white_primary0)
                .statusBarsPadding()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (onBackPressed != null) {
                IconButton(
                    modifier = Modifier.size(36.dp),
                    onClick = { onBackPressed() },
                ) {
                    Icon(
                        modifier = Modifier.height(24.dp),
                        painter = painterResource(Res.drawable.ic_arrow_back),
                        contentDescription = "Back",
                        tint = textColor,
                    )
                }
            } else {
                KamelImage(
                    modifier = Modifier.align(Alignment.CenterVertically).size(36.dp),
                    resource = Resource.Success(painterResource(Res.drawable.studyround_logo)),
                    contentDescription = "Logo",
                )
            }

            Text(
                modifier = Modifier.align(Alignment.CenterVertically)
                    .padding(start = 16.dp),
                text = title,
                color = textColor,
                style = StudyRoundTheme.typography.bodySmall,
            )
        }
    }
}
