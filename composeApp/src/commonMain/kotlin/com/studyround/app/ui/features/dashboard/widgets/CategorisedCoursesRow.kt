package com.studyround.app.ui.features.dashboard.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import com.studyround.app.domain.model.Course
import com.studyround.app.ui.composables.buttons.LinkTextButton
import com.studyround.app.ui.composables.buttons.SecondaryButton
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import studyround.composeapp.generated.resources.*
import kotlin.math.roundToInt

@Composable
fun CategorisedCoursesRow(
    modifier: Modifier = Modifier,
    categoryTitle: String,
    courses: List<Course>,
    viewAllClicked: () -> Unit,
    viewCourseClicked: (Course) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(start = 16.dp).weight(1f),
                text = categoryTitle,
                style = StudyRoundTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
            )

            LinkTextButton(
                modifier = Modifier.padding(end = 8.dp),
                text = stringResource(Res.string.view_more_link),
                textColor = StudyRoundTheme.colors.deviation_primary1_primary4,
                showUnderline = false,
            ) { viewAllClicked() }
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(courses.size) { index ->
                CourseCard(course = courses[index]) { viewCourseClicked(it) }
            }
        }
    }
}

@Composable
private fun CourseCard(course: Course, viewCourseClicked: (Course) -> Unit) {
    Box(
        modifier = Modifier
            .width(320.dp)
            .requiredHeight(180.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable { viewCourseClicked(course) },
    ) {
        Image(
            painter = rememberImagePainter(course.imageUrl.orEmpty()),
            contentDescription = course.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = course.title,
                maxLines = 2,
                style = StudyRoundTheme.typography.titleExtraSmall.copy(fontWeight = FontWeight.SemiBold),
                color = StudyRoundTheme.colors.white,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(8.dp))

            RatingBar(rating = course.rating)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = course.formattedPrice,
                style = StudyRoundTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = StudyRoundTheme.colors.deviation_primary0_primary4,
            )

            Spacer(modifier = Modifier.height(12.dp))

            SecondaryButton(
                text = stringResource(Res.string.take_session),
                isSmallSize = true,
            ) { viewCourseClicked(course) }
        }
    }
}

@Composable
private fun RatingBar(rating: Float) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { index ->
            Icon(
                painter = painterResource(Res.drawable.ic_star),
                contentDescription = null,
                tint = if (index < rating.roundToInt())
                    StudyRoundTheme.colors.tertiary
                else
                    StudyRoundTheme.colors.gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
