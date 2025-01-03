package com.studyround.app.ui.features.auth

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.studyround.app.ui.features.auth.login.LoginScreen
import com.studyround.app.ui.features.auth.otp.OtpScreen
import com.studyround.app.ui.features.auth.register.RegisterScreen
import com.studyround.app.ui.navigation.navigateToRoute

@Composable
fun AuthScreen() {
    // Nav controller for the children of AuthScreen's NavHost
    val authNavController: NavHostController = rememberNavController()

    NavHost(
        navController = authNavController,
        startDestination = AuthDestination.Login,
        modifier = Modifier.fillMaxSize(),
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { slideOutHorizontally { -it } },
        popEnterTransition = { slideInHorizontally { -it } },
        popExitTransition = { slideOutHorizontally { it } },
    ) {
        composable<AuthDestination.Login>{
            LoginScreen(
                onNavigate = { route, replace ->
                    authNavController.navigateToRoute(route, replace)
                }
            )
        }
        composable<AuthDestination.OTP>{
            OtpScreen(
                onNavigate = { route, replace ->
                    authNavController.navigateToRoute(route, replace)
                },
                onGoBack = {
                    authNavController.popBackStack()
                }
            )
        }
        composable<AuthDestination.Register> {
            RegisterScreen()
        }
        composable<AuthDestination.ResetPassword> {

        }
        composable<AuthDestination.ForgotPassword> {

        }
    }
}
