package com.studyround.app.ui.composables.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import studyround.composeapp.generated.resources.studyround_logo

@Composable
fun StudyRoundTextLogo(
    modifier: Modifier = Modifier,
    textColor: Color = StudyRoundTheme.colors.deviation_tone4_tone5,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        KamelImage(
            modifier = Modifier.align(Alignment.CenterVertically).size(36.dp),
            resource = Resource.Success(painterResource(Res.drawable.studyround_logo)),
            contentDescription = "Logo",
        )

        Text(
            modifier = Modifier.align(Alignment.CenterVertically)
                .padding(start = 8.dp),
            text = "studyround",
            color = textColor,
            style = StudyRoundTheme.typography.titleSmall,
        )
    }
}
