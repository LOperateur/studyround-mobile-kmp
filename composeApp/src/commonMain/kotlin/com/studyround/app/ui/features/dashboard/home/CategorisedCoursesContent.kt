package com.studyround.app.ui.features.dashboard.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.studyround.app.domain.model.Category
import com.studyround.app.domain.model.Course
import com.studyround.app.ui.composables.common.PullRefreshContainer
import com.studyround.app.ui.features.dashboard.widgets.CategorisedCoursesRow

@Composable
fun CategorisedCoursesContent(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    viewCoursesInCategoryClicked: (Category) -> Unit,
    viewCourseClicked: (Course) -> Unit,
    isRefreshingCourses: Boolean,
    listRefreshed: () -> Unit,
) {
    val scrollState = rememberScrollState()

    PullRefreshContainer(
        modifier = modifier,
        isRefreshing = isRefreshingCourses,
        onRefreshTriggered = listRefreshed,
    ) {
        Column(
            modifier = Modifier.verticalScroll(scrollState).padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(36.dp),
        ) {
            categories.forEach {
                CategorisedCoursesRow(
                    categoryTitle = it.name,
                    courses = it.coursesInCategory,
                    viewAllClicked = { viewCoursesInCategoryClicked(it) },
                    viewCourseClicked = viewCourseClicked,
                )
            }
        }
    }
}
