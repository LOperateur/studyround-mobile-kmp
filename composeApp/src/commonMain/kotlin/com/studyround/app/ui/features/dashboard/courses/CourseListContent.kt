package com.studyround.app.ui.features.dashboard.courses

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.studyround.app.domain.model.Course
import com.studyround.app.ui.composables.buttons.SecondaryButton
import com.studyround.app.ui.features.dashboard.widgets.CourseListItem
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.*

@Composable
fun CourseListContent(
    modifier: Modifier = Modifier,
    courses: List<Course>,
    canLoadMoreCourses: Boolean,
    isLoadingMoreCourses: Boolean,
    loadMoreError: Boolean,
    openCourseClicked: (Course) -> Unit,
    loadMoreClicked: () -> Unit,
) {

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp),
    ) {
        items(courses) {
            CourseListItem(course = it) {
                openCourseClicked(it)
            }
        }

        item {
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth().animateContentSize().padding(bottom = 24.dp),
                visible = canLoadMoreCourses,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isLoadingMoreCourses) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(8.dp).size(32.dp),
                            strokeWidth = 2.dp,
                            color = StudyRoundTheme.colors.deviation_primary1_white,
                        )
                    } else if (loadMoreError) {
                        ErrorLoadingMore { loadMoreClicked() }
                    } else {
                        SecondaryButton(text = stringResource(Res.string.load_more_prompt)) {
                            loadMoreClicked()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorLoadingMore(loadMoreClicked: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(14.dp),
            painter = painterResource(resource = Res.drawable.ic_warning),
            tint = StudyRoundTheme.colors.danger,
            contentDescription = "Warning sign"
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = stringResource(Res.string.more_courses_fetch_error),
            style = StudyRoundTheme.typography.bodyExtraSmall,
        )

        Spacer(Modifier.width(12.dp))

        SecondaryButton(
            text = stringResource(Res.string.retry_prompt),
            tintIcons = true,
            iconEnd = painterResource(Res.drawable.ic_reload),
            isSmallSize = true,
        ) {
            loadMoreClicked()
        }
    }
}
