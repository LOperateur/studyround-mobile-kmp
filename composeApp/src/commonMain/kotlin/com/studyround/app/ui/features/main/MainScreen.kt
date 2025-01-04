package com.studyround.app.ui.features.main

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.studyround.app.ui.features.dashboard.DashboardScreen

@Composable
fun MainScreen() {
    // Nav controller for the children of MainScreen's NavHost
    val mainNavController: NavHostController = rememberNavController()

    NavHost(
        navController = mainNavController,
        startDestination = MainDestination.Dashboard,
        modifier = Modifier.fillMaxSize(),
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { slideOutHorizontally { -it } },
        popEnterTransition = { slideInHorizontally { -it } },
        popExitTransition = { slideOutHorizontally { it } },
    ) {
        composable<MainDestination.Dashboard> {
            DashboardScreen()
        }
        composable<MainDestination.Account> {

        }
        composable<MainDestination.Session> {

        }
    }
}
