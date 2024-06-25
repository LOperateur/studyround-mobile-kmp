package com.studyround.app.ui.features.dashboard.courses

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.studyround.app.domain.model.Course
import com.studyround.app.ui.composables.buttons.LinkTextButton
import com.studyround.app.ui.features.dashboard.widgets.CourseListItem
import com.studyround.app.ui.theme.StudyRoundTheme

@Composable
fun CourseListContent(
    modifier: Modifier = Modifier,
    courses: List<Course>,
    canLoadMoreCourses: Boolean,
    isLoadingMoreCourses: Boolean,
    openCourseClicked: (Course) -> Unit,
    loadMoreClicked: () -> Unit,
) {

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(36.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
    ) {
        items(courses) {
            CourseListItem(course = it) {
                openCourseClicked(it)
            }
        }

        item {
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = canLoadMoreCourses,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isLoadingMoreCourses) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(4.dp).size(32.dp),
                            strokeWidth = 2.dp,
                            color = StudyRoundTheme.colors.deviation_primary1_white,
                        )
                    } else {
                        LinkTextButton(text = "Load more", showUnderline = false) {
                            loadMoreClicked()
                        }
                    }
                }
            }
        }
    }
}
