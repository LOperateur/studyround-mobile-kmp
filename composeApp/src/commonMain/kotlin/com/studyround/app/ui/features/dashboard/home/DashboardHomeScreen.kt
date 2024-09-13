package com.studyround.app.ui.features.dashboard.home

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.studyround.app.ui.composables.buttons.PrimaryButton
import com.studyround.app.ui.features.dashboard.courses.CoursesScreen
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import studyround.composeapp.generated.resources.*

class DashboardHomeScreen : Tab {

    @Composable
    override fun Content() {
        val vm = koinViewModel<DashboardHomeViewModel>()
        val viewState by vm.viewState.collectAsState()
        val eventProcessor = vm::processEvent
        val dashboardTabNavigator = LocalTabNavigator.current

        LaunchedEffect(Unit) {
            vm.viewEffects.collect { effect ->
                when (effect) {
                    is NavigateToCourse -> {
                        dashboardTabNavigator.current = CoursesScreen()
                    }

                    is NavigateToCoursesInCategory -> {
                        dashboardTabNavigator.current = CoursesScreen()
                    }
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (viewState.loadingWithoutData) {
                Box(modifier = Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        strokeWidth = 4.dp,
                        color = StudyRoundTheme.colors.deviation_primary1_white,
                    )
                }
            }

            AnimatedVisibility(
                modifier = Modifier.matchParentSize(),
                visible = viewState.errorWithoutData,
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
                            eventProcessor(RetryLoadTriggered(isRefresh = false))
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = viewState.hasData,
                enter = fadeIn(animationSpec = tween(750)),
            ) {
                CategorisedCoursesContent(
                    categories = viewState.categorisedCourses,
                    viewCoursesInCategoryClicked = { eventProcessor(ViewCategoryClicked(it)) },
                    viewCourseClicked = { eventProcessor(CourseClicked(it)) },
                    isRefreshingCourses = viewState.refreshLoading || viewState.loadingWithData,
                    listRefreshed = { eventProcessor(RetryLoadTriggered(isRefresh = true)) },
                )
            }
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.home)
            val icon = painterResource(Res.drawable.ic_home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon,
                )
            }
        }
}
