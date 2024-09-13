package com.studyround.app.ui.features.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.studyround.app.ui.composables.common.StudyRoundBackground
import com.studyround.app.ui.composables.common.appbar.AppBarViewModel
import com.studyround.app.ui.composables.common.appbar.StudyRoundAppBar
import com.studyround.app.ui.features.dashboard.courses.CoursesScreen
import com.studyround.app.ui.features.dashboard.home.DashboardHomeScreen
import com.studyround.app.ui.theme.StudyRoundTheme
import com.studyround.app.ui.utils.isTabletLandscapeMode
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import studyround.composeapp.generated.resources.*

class DashboardNavScreen : Screen {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    override fun Content() {
        // val homeNavigator = LocalNavigator.currentOrThrow
        val appBarViewModel = koinViewModel<AppBarViewModel>()
        val windowSizeClass = calculateWindowSizeClass()

        val isExpanded = windowSizeClass.isTabletLandscapeMode()

        TabNavigator(DashboardHomeScreen()) {
            val tabNavigator = LocalTabNavigator.current

            Scaffold(
                // Adjust nav bar paddings for devices that put left/right nav bars in landscape
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
                ),
                // Note: Scaffold automatically applies topBar padding to content
                content = {
                    Row {
                        if (isExpanded) {
                            SideNavigationBar()
                        }

                        Column(modifier = Modifier.clip(RectangleShape)) {
                            StudyRoundAppBar(
                                title = tabNavigator.current.options.title,
                                viewModel = appBarViewModel,
                                hideLogo = isExpanded,
                            )

                            Box {
                                StudyRoundBackground(
                                    Modifier.fillMaxSize(),
                                    showGradientScrim = true,
                                )
                                Box(modifier = Modifier.padding(it)) { CurrentTab() }
                            }
                        }
                    }
                },
                bottomBar = {
                    Box(
                        modifier = Modifier
                            .background(color = StudyRoundTheme.colors.deviation_primary3_primary0)
                            .navigationBarsPadding()
                    ) {
                        if (!isExpanded) BottomNavigationBar() else Spacer(Modifier.fillMaxWidth())
                    }
                }
            )
        }
    }

    @Composable
    private fun BottomNavigationBar() {
        BottomNavigation(
            backgroundColor = StudyRoundTheme.colors.deviation_primary3_primary0,
            contentColor = StudyRoundTheme.colors.white,
            elevation = 0.dp,
        ) {
            TabNavigationItem(DashboardHomeScreen())
            TabNavigationItem(CoursesScreen())
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

    @Composable
    private fun SideNavigationBar() {
        NavigationRail(
            modifier = Modifier.fillMaxHeight(),
            backgroundColor = StudyRoundTheme.colors.deviation_primary2_primary0,
            contentColor = StudyRoundTheme.colors.white,
            header = {
                Image(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
                        .size(36.dp),
                    painter = painterResource(Res.drawable.studyround_logo),
                    contentDescription = "Logo",
                )
            },
            elevation = 0.dp,
        ) {
            Spacer(Modifier.weight(1f))
            SideNavigationItem(DashboardHomeScreen())
            Spacer(Modifier.height(24.dp))
            SideNavigationItem(CoursesScreen())
            Spacer(Modifier.weight(1.2f))
        }
    }

    @Composable
    private fun SideNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current
        val isSelected = tabNavigator.current.key == tab.key

        NavigationRailItem(
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
