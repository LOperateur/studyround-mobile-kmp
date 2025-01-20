package com.studyround.app.ui.navigation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.ComposeNavigatorDestinationBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.get
import kotlin.reflect.KClass
import kotlin.reflect.KType

@Composable
fun ModalBottomSheetNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    route: KClass<*>? = null,
    typeMap: Map<KType, NavType<*>> = emptyMap(),
    builder: NavGraphBuilder.() -> Unit
) {
    val startDestination = EmptyDestination

    // Create an empty composable screen destination
    val emptyDestination = ComposeNavigatorDestinationBuilder(
        navigator = navController.navigatorProvider[ComposeNavigator::class],
        route = startDestination::class,
        typeMap = emptyMap(),
        content = {},
    ).build()

    // Build the NavGraph
    val navGraph = remember(route, startDestination, builder) {
        NavGraphBuilder(
            provider = navController.navigatorProvider,
            startDestination = startDestination,
            route = route,
            typeMap = typeMap,
        ).apply { addDestination(emptyDestination) }.apply(builder).build()
    }

    // Create the Modal Bottom Sheet Layout with the provided BottomSheetNavigator
    CompositionLocalProvider(LocalElevationOverlay provides null) {
        ModalBottomSheetLayout(
            bottomSheetNavigator = navController.navigatorProvider[BottomSheetNavigator::class],
            sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        ) {
            NavHost(
                modifier = modifier,
                navController = navController,
                graph = navGraph,
            )
        }
    }
}

@Composable
fun rememberBottomSheetNavigator(
    animationSpec: AnimationSpec<Float> = SpringSpec(),
    skipHalfExpanded: Boolean = false,
): BottomSheetNavigator {
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        animationSpec = animationSpec,
        skipHalfExpanded = skipHalfExpanded,
    )
    return remember(sheetState) { BottomSheetNavigator(sheetState) }
}
