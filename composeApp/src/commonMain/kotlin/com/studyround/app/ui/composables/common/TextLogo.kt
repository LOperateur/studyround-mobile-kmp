package com.studyround.app.ui.composables.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.studyround.app.MR
import com.studyround.app.ui.theme.StudyRoundTheme
import dev.icerock.moko.resources.compose.painterResource
import io.kamel.core.Resource
import io.kamel.image.KamelImage

@Composable
fun StudyRoundTextLogo(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        KamelImage(
            modifier = Modifier.align(Alignment.CenterVertically).size(56.dp),
            resource = Resource.Success(painterResource(MR.images.studyround_logo)),
            contentDescription = "Logo",
        )

        Text(
            modifier = Modifier.align(Alignment.CenterVertically)
                .padding(start = 8.dp),
            text = "studyround",
            style = StudyRoundTheme.typography.titleSmall,
        )
    }
}
