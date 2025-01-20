package com.studyround.app.ui.features.dashboard.courses

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.studyround.app.ui.composables.alert.LocalAlertManager
import com.studyround.app.ui.composables.buttons.PrimaryButton
import com.studyround.app.ui.features.dashboard.courses.details.CourseDetailsBottomSheet
import com.studyround.app.ui.navigation.ModalBottomSheetNavHost
import com.studyround.app.ui.navigation.bottomSheet
import com.studyround.app.ui.navigation.navigateToRoute
import com.studyround.app.ui.navigation.rememberBottomSheetNavigator
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import studyround.composeapp.generated.resources.*

@Composable
fun CoursesScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        val vm = koinViewModel<CoursesViewModel>()
        val viewState by vm.viewState.collectAsState()
        val eventProcessor = vm::processEvent

        val alertManager = LocalAlertManager.current

        val bottomSheetNavigator = rememberBottomSheetNavigator(skipHalfExpanded = true)

        // Nav controller for the children of CoursesScreen's NavHost
        val coursesNavController: NavHostController = rememberNavController(bottomSheetNavigator)

        LaunchedEffect(Unit) {
            vm.viewEffects.collect { effect ->
                when (effect) {
                    is ShowAlert -> {
                        alertManager.show(
                            effect.message.loadString(),
                            effect.type,
                        )
                    }
                    is Navigate -> {
                        coursesNavController.navigateToRoute(
                            CoursesBottomSheetDestination.CourseDetailsSheet(
                                courseId = effect.courseId
                            )
                        )
                    }
                }
            }
        }

        // Loading
        if (viewState.loadingWithoutData) {
            Box(modifier = Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    strokeWidth = 4.dp,
                    color = StudyRoundTheme.colors.deviation_primary1_white,
                )
            }
        }

        // Error
        AnimatedVisibility(
            modifier = Modifier.matchParentSize(),
            visible = viewState.error,
            enter = fadeIn(animationSpec = tween(750)),
            exit = fadeOut(animationSpec = tween(750)),
        ) {
            Box(modifier = Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        modifier = Modifier.padding(8.dp).size(72.dp).alpha(0.5f),
                        painter = painterResource(Res.drawable.ic_wifi_off),
                        tint = StudyRoundTheme.colors.deviation_tone4_tone5,
                        contentDescription = "",
                    )
                    Text(
                        text = stringResource(Res.string.fetch_courses_network_error),
                        style = StudyRoundTheme.typography.labelMedium,
                        textAlign = TextAlign.Center,
                    )
                    PrimaryButton(
                        modifier = Modifier.padding(12.dp),
                        text = stringResource(Res.string.retry_prompt),
                        tintIcons = true,
                        iconEnd = painterResource(Res.drawable.ic_reload),
                    ) {
                        eventProcessor(RetryLoadTriggered)
                    }
                }
            }
        }

        // Empty
        AnimatedVisibility(
            modifier = Modifier.matchParentSize(),
            visible = viewState.showEmptyState,
            enter = fadeIn(animationSpec = tween(750)),
            exit = fadeOut(animationSpec = tween(750)),
        ) {
            Box(modifier = Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        modifier = Modifier.padding(8.dp).size(72.dp).alpha(0.5f),
                        painter = painterResource(Res.drawable.ic_folder),
                        tint = StudyRoundTheme.colors.deviation_tone4_tone5,
                        contentDescription = "",
                    )
                    Text(
                        text = stringResource(Res.string.empty_courses_text),
                        style = StudyRoundTheme.typography.labelMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

        // Content
        AnimatedVisibility(
            visible = viewState.courses.isNotEmpty(),
            enter = fadeIn(animationSpec = tween(750)),
            exit = fadeOut(animationSpec = tween(750)),
        ) {
            CourseListContent(
                courses = viewState.courses,
                canLoadMoreCourses = viewState.canLoadMore,
                isLoadingMoreCourses = viewState.loadingMore,
                loadMoreError = viewState.loadMoreError,
                openCourseClicked = { eventProcessor(CourseClicked(it.id)) },
                loadMoreClicked = { eventProcessor(LoadMoreClicked) },
                isRefreshingCourses = viewState.refreshLoading || viewState.loadingWithData,
                listRefreshed = { eventProcessor(RefreshTriggered) }
            )
        }

        ModalBottomSheetNavHost(
            navController = coursesNavController,
            modifier = Modifier.fillMaxSize(),
        ) {
            bottomSheet<CoursesBottomSheetDestination.CourseDetailsSheet> {
                CourseDetailsBottomSheet {
                    coursesNavController.navigateUp()
                }
            }
        }
    }
}
