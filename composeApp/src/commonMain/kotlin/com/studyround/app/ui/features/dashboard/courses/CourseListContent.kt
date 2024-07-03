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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.studyround.app.domain.model.Course
import com.studyround.app.ui.composables.buttons.SecondaryButton
import com.studyround.app.ui.composables.common.PullRefreshContainer
import com.studyround.app.ui.features.dashboard.widgets.CourseListItem
import com.studyround.app.ui.theme.StudyRoundTheme
import com.studyround.app.ui.utils.isAtLeastMediumWidth
import com.studyround.app.ui.utils.isTabletLandscapeMode
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.*

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun CourseListContent(
    modifier: Modifier = Modifier,
    courses: List<Course>,
    canLoadMoreCourses: Boolean,
    isLoadingMoreCourses: Boolean,
    loadMoreError: Boolean,
    openCourseClicked: (Course) -> Unit,
    loadMoreClicked: () -> Unit,
    isRefreshingCourses: Boolean,
    listRefreshed: () -> Unit,
) {
    val windowSizeClass = calculateWindowSizeClass()

    val gridColumnCount = when {
        windowSizeClass.isTabletLandscapeMode() -> 3
        windowSizeClass.isAtLeastMediumWidth() -> 2
        else -> 1
    }

    val heightList = remember(gridColumnCount) { mutableStateMapOf<Int, Dp>() }
    val density = LocalDensity.current

    PullRefreshContainer(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefreshingCourses,
        onRefreshTriggered = listRefreshed,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(gridColumnCount),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp),
        ) {
            itemsIndexed(courses) { index, course ->
                CourseListItem(
                    course = course,
                    modifier = Modifier
                        .requiredHeightIn(
                            min = if (gridColumnCount > 1)
                                heightList[index / gridColumnCount] ?: 0.dp
                            else
                                0.dp
                        )
                        .onGloballyPositioned {
                            if (gridColumnCount > 1) {
                                with(density) {
                                    heightList[index / gridColumnCount] =
                                        max(
                                            it.size.height.toDp(),
                                            heightList[index / gridColumnCount] ?: 0.dp,
                                        )
                                }
                            }
                        },
                ) {
                    openCourseClicked(course)
                }
            }

            item(span = { GridItemSpan(gridColumnCount) }) {
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
