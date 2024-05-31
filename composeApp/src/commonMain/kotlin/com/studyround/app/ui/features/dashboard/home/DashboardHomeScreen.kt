package com.studyround.app.ui.features.dashboard.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.studyround.app.ui.features.dashboard.courses.CoursesScreen
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import studyround.composeapp.generated.resources.*

class DashboardHomeScreen(val boolean: Boolean = true) : Tab {

    @Composable
    override fun Content() {
        val vm = getScreenModel<DashboardHomeViewModel>()
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
            contentAlignment = Alignment.Center,
        ) {
            if (viewState.loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    strokeWidth = 4.dp,
                    color = StudyRoundTheme.colors.deviation_primary1_white,
                )
            } else {
                CategorisedCoursesContent(
                    categories = viewState.categorisedCourses,
                    viewCoursesInCategoryClicked = { eventProcessor(ViewCategoryClicked(it)) },
                    viewCourseClicked = { eventProcessor(CourseClicked(it)) },
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
