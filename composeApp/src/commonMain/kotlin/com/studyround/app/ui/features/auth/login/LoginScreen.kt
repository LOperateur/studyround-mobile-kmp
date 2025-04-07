package com.studyround.app.ui.features.auth.login

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.studyround.app.ui.composables.alert.LocalAlertManager
import com.studyround.app.ui.features.auth.AuthDestination
import com.studyround.app.ui.features.auth.login.compact.CompactLoginScreen
import com.studyround.app.ui.features.auth.login.expanded.ExpandedLoginScreen
import com.studyround.app.ui.features.auth.otp.OtpScreen
import com.studyround.app.ui.navigation.EmptyDestination
import com.studyround.app.ui.navigation.navigateToRoute
import com.studyround.app.ui.utils.isTabletLandscapeMode
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun LoginScreen(
    onNavigate: (destination: AuthDestination, shouldReplace: Boolean) -> Unit,
) {
    val vm = koinViewModel<LoginViewModel>()
    val viewState by vm.viewState.collectAsState()
    val textFieldState = vm.loginTextFieldState

    val alertManager = LocalAlertManager.current
    val windowSizeClass = calculateWindowSizeClass()

    val isExpanded = windowSizeClass.isTabletLandscapeMode()

    LaunchedEffect(Unit) {
        vm.viewEffects.collect { effect ->
            when (effect) {
                is ShowAlert -> {
                    alertManager.show(
                        effect.message.loadString(*effect.args),
                        effect.type,
                    )
                }

                is Navigate -> {
                    onNavigate(effect.destination, effect.shouldReplace)
                }
            }
        }
    }


    Box {
        if (isExpanded) {
            ExpandedLoginScreen(
                viewState = viewState,
                textFieldState = textFieldState,
                eventProcessor = vm::processEvent,
            )
        } else {
            CompactLoginScreen(
                viewState = viewState,
                textFieldState = textFieldState,
                eventProcessor = vm::processEvent,
            )
        }
    }

    val loginNavController: NavHostController = rememberNavController()

    NavHost(
        navController = loginNavController,
        startDestination = EmptyDestination,
        modifier = Modifier.fillMaxSize(),
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { slideOutHorizontally { -it } },
        popEnterTransition = { slideInHorizontally { -it } },
        popExitTransition = { slideOutHorizontally { it } },
    ) {
        composable<EmptyDestination> {}

        composable<AuthDestination.OTP>{
            OtpScreen(
                onNavigate = { route, replace ->
                    loginNavController.navigateToRoute(route, replace)
                },
                onGoBack = {
                    loginNavController.popBackStack()
                }
            )
        }
        composable<AuthDestination.ResetPassword> {

        }
        composable<AuthDestination.ForgotPassword> {

        }
    }
}
