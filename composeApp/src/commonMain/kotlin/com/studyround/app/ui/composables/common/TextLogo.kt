package com.studyround.app.ui.composables.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.theme.StudyRoundTheme
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import org.jetbrains.compose.resources.painterResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.*

@Composable
fun StudyRoundTextLogo(
    modifier: Modifier = Modifier,
    color: Color = StudyRoundTheme.colors.deviation_tone4_tone5,
    onBackPressed: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Todo: Make this look better
        if (onBackPressed != null) {
            IconButton(
                modifier = Modifier.width(24.dp),
                onClick = { onBackPressed() },
            ) {
                Icon(
                    modifier = Modifier.height(24.dp),
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    tint = color,
                )
            }
        }

        KamelImage(
            modifier = Modifier.align(Alignment.CenterVertically).size(36.dp),
            resource = Resource.Success(painterResource(Res.drawable.studyround_logo)),
            contentDescription = "Logo",
        )

        Text(
            modifier = Modifier.align(Alignment.CenterVertically)
                .padding(start = 8.dp),
            text = "studyround",
            color = color,
            style = StudyRoundTheme.typography.titleSmall,
        )
    }
}
