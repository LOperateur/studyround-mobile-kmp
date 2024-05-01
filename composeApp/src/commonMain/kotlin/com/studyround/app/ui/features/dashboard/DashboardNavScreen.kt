package com.studyround.app.ui.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.studyround.app.ui.features.dashboard.courses.CoursesScreen
import com.studyround.app.ui.features.dashboard.home.DashboardHomeScreen
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.painterResource
import studyround.composeapp.generated.resources.*

class DashboardNavScreen : Screen {

    @Composable
    override fun Content() {
        // val homeNavigator = LocalNavigator.currentOrThrow

        TabNavigator(DashboardHomeScreen()) {
            Scaffold(
                content = {
                    Box(modifier = Modifier.padding(it)) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    Box(
                        modifier = Modifier.background(
                            color = StudyRoundTheme.colors.deviation_primary3_primary0
                        )
                    ) {
                        BottomNavigation(
                            modifier = Modifier.navigationBarsPadding(),
                            backgroundColor = StudyRoundTheme.colors.deviation_primary3_primary0,
                            contentColor = StudyRoundTheme.colors.white,
                            elevation = 0.dp,
                        ) {
                            TabNavigationItem(DashboardHomeScreen())
                            TabNavigationItem(CoursesScreen())
                        }
                    }
                }
            )
        }
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current
        val isSelected = tabNavigator.current.key == tab.key

        BottomNavigationItem(
            selected = isSelected,
            onClick = { tabNavigator.current = tab },
            label = {
                Text(
                    text = tab.options.title,
                    style = StudyRoundTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = if (isSelected)
                        StudyRoundTheme.colors.deviation_secondary1_secondary3
                    else
                        StudyRoundTheme.colors.white,
                )
            },
            icon = {
                Icon(
                    painter = tab.options.icon ?: painterResource(Res.drawable.ic_close),
                    contentDescription = tab.options.title,
                    tint = if (isSelected)
                        StudyRoundTheme.colors.deviation_secondary1_secondary3
                    else
                        StudyRoundTheme.colors.white,
                )
            }
        )
    }

}
