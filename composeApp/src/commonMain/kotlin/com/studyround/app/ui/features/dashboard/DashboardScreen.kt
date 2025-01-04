package com.studyround.app.ui.features.dashboard

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.studyround.app.ui.composables.common.StudyRoundBackground
import com.studyround.app.ui.composables.common.appbar.StudyRoundAppBar
import com.studyround.app.ui.features.dashboard.courses.CoursesScreen
import com.studyround.app.ui.features.dashboard.home.HomeScreen
import com.studyround.app.ui.navigation.navigateToTab
import com.studyround.app.ui.theme.StudyRoundTheme
import com.studyround.app.ui.utils.isTabletLandscapeMode
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import studyround.composeapp.generated.resources.*

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun DashboardScreen() {
    val windowSizeClass = calculateWindowSizeClass()

    val isExpanded = windowSizeClass.isTabletLandscapeMode()

    // Nav controller for the children of DashboardScreen's NavHost
    val dashboardNavController: NavHostController = rememberNavController()

    val navBackStackEntry by dashboardNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val dashboardNavigationItems: List<DashboardNavigationItem> = listOf(
        DashboardNavigationItem.HomeTab, DashboardNavigationItem.CoursesTab
    )

    val title by remember {
        derivedStateOf {
            dashboardNavigationItems.firstOrNull {
                navBackStackEntry?.destination?.hasRoute(it.topLevelRoute::class) == true
            }?.titleRes
        }
    }

    Scaffold(
        // Adjust nav bar paddings for devices that put left/right nav bars in landscape
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
        ),
        // Note: Scaffold automatically applies topBar padding to content
        content = {
            Row {
                if (isExpanded) {
                    SideNavigationBar(currentDestination) {
                        dashboardNavController.navigateToTab(
                            it.topLevelRoute,
                        )
                    }
                }

                Column(modifier = Modifier.clip(RectangleShape)) {
                    StudyRoundAppBar(
                        title = title?.let { stringResource(it) }.orEmpty(),
                        hideLogo = isExpanded,
                    )

                    Box {
                        StudyRoundBackground(showGradientScrim = true)

                        Box(modifier = Modifier.padding(it)) {
                            DashboardNavHost(dashboardNavController)
                        }
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
                if (!isExpanded) {
                    BottomNavigationBar(currentDestination) {
                        dashboardNavController.navigateToTab(
                            it.topLevelRoute,
                        )
                    }
                } else {
                    Spacer(Modifier.fillMaxWidth())
                }
            }
        }
    )
}

@Composable
private fun DashboardNavHost(dashboardNavController: NavHostController) {
    NavHost(
        navController = dashboardNavController,
        startDestination = DashboardDestination.Home,
        modifier = Modifier.fillMaxSize(),
        enterTransition = { fadeIn(tween(0)) },
        exitTransition = { fadeOut(tween(0)) },
    ) {
        composable<DashboardDestination.Home> {
            HomeScreen(
                onNavigateToCourse = {
                    dashboardNavController.navigateToTab(
                        DashboardDestination.Courses(selectedCourseId = it.id),
                        resetState = true,
                    )
                },
                onNavigateToCourseCategory = {
                    dashboardNavController.navigateToTab(
                        DashboardDestination.Courses(selectedCategoryId = it.id),
                        resetState = true,
                    )
                },
            )
        }
        composable<DashboardDestination.Courses> {
            CoursesScreen()
        }
    }
}

@Composable
private fun BottomNavigationBar(
    currentDestination: NavDestination?,
    onTabClicked: (item: DashboardNavigationItem) -> Unit,
) {
    BottomNavigation(
        backgroundColor = StudyRoundTheme.colors.deviation_primary3_primary0,
        contentColor = StudyRoundTheme.colors.white,
        elevation = 0.dp,
    ) {
        TabNavigationItem(DashboardNavigationItem.HomeTab, currentDestination, onTabClicked)
        TabNavigationItem(DashboardNavigationItem.CoursesTab, currentDestination, onTabClicked)
    }
}

@Composable
private fun RowScope.TabNavigationItem(
    tab: DashboardNavigationItem,
    currentDestination: NavDestination?,
    onTabClicked: (item: DashboardNavigationItem) -> Unit,
) {
    val isSelected =
        currentDestination?.hierarchy?.any { it.hasRoute(tab.topLevelRoute::class) } == true

    BottomNavigationItem(
        selected = isSelected,
        onClick = { onTabClicked(tab) },
        label = {
            Text(
                text = stringResource(tab.titleRes),
                style = StudyRoundTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = if (isSelected)
                    StudyRoundTheme.colors.deviation_secondary1_secondary3
                else
                    StudyRoundTheme.colors.white,
            )
        },
        icon = {
            Icon(
                painter = painterResource(tab.iconRes),
                contentDescription = stringResource(tab.titleRes),
                tint = if (isSelected)
                    StudyRoundTheme.colors.deviation_secondary1_secondary3
                else
                    StudyRoundTheme.colors.white,
            )
        }
    )
}

@Composable
private fun SideNavigationBar(
    currentDestination: NavDestination?,
    onTabClicked: (item: DashboardNavigationItem) -> Unit,
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        backgroundColor = StudyRoundTheme.colors.deviation_primary2_primary0,
        contentColor = StudyRoundTheme.colors.white,
        header = {
            Image(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding()
                    .size(36.dp),
                painter = painterResource(Res.drawable.studyround_logo),
                contentDescription = "Logo",
            )
        },
        elevation = 0.dp,
    ) {
        Spacer(Modifier.weight(1f))
        SideNavigationItem(DashboardNavigationItem.HomeTab, currentDestination, onTabClicked)
        Spacer(Modifier.height(24.dp))
        SideNavigationItem(DashboardNavigationItem.CoursesTab, currentDestination, onTabClicked)
        Spacer(Modifier.weight(1.2f))
    }
}

@Composable
private fun SideNavigationItem(
    tab: DashboardNavigationItem,
    currentDestination: NavDestination?,
    onTabClicked: (item: DashboardNavigationItem) -> Unit,
) {
    val isSelected =
        currentDestination?.hierarchy?.any { it.hasRoute(tab.topLevelRoute::class) } == true

    NavigationRailItem(
        selected = isSelected,
        onClick = { onTabClicked(tab) },
        label = {
            Text(
                text = stringResource(tab.titleRes),
                style = StudyRoundTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = if (isSelected)
                    StudyRoundTheme.colors.deviation_secondary1_secondary3
                else
                    StudyRoundTheme.colors.white,
            )
        },
        icon = {
            Icon(
                painter = painterResource(tab.iconRes),
                contentDescription = stringResource(tab.titleRes),
                tint = if (isSelected)
                    StudyRoundTheme.colors.deviation_secondary1_secondary3
                else
                    StudyRoundTheme.colors.white,
            )
        }
    )
}
