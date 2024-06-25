package com.studyround.app.ui.features.dashboard.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import com.studyround.app.domain.model.Course
import com.studyround.app.ui.composables.modifiers.neuSurface
import com.studyround.app.ui.theme.StudyRoundTheme

@Composable
fun CourseListItem(
    modifier: Modifier = Modifier,
    course: Course,
    onCourseClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .neuSurface(cornerRadius = 8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .clickable { onCourseClicked() }
            .background(color = StudyRoundTheme.colors.deviation_tone1_primary1)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.CenterVertically)
                    .clip(RoundedCornerShape(8.dp)),
                painter = rememberImagePainter(course.imageUrl.orEmpty()), // Todo: Loading spinner
                contentDescription = course.title,
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text(
                    text = course.title,
                    maxLines = 4,
                    style = StudyRoundTheme.typography.bodySmall,
                    color = StudyRoundTheme.colors.deviation_tone4_tone5,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                )

                Text(
                    text = course.creatorUsername,
                    maxLines = 1,
                    style = StudyRoundTheme.typography.labelSmall,
                    color = StudyRoundTheme.colors.deviation_tone4_tone5,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
