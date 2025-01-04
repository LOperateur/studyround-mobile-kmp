package com.studyround.app.ui.navigation

import androidx.core.bundle.Bundle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.serialization.decodeArguments
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

/**
 * Navigates to the given destination screen.
 *
 * If [shouldReplace] is set to true, it replaces the current screen.
 * Otherwise, it pushes the new destination onto the stack.
 *
 * @param route The [Destination] to navigate to.
 * @param shouldReplace Flag to replace the current screen or push
 */
fun <T : Destination> NavController.navigateToRoute(route: T, shouldReplace: Boolean = false) {
    if (shouldReplace) {
        navigateReplace(route)
    } else {
        navigatePush(route)
    }
}

/**
 * Replaces the current screen with the given destination screen. The previous screen's state is
 * not saved or added to the backstack
 *
 * This function does nothing if the [route] Destination being navigated to is already the current destination.
 *
 * @param route The [Destination] to replace the current screen with.
 */
fun <T : Destination> NavController.navigateReplace(route: T) {
    val currentDestination = currentBackStackEntry?.destination
    val isCurrentRoute = currentDestination?.hasRoute(route::class) == true

    if (!isCurrentRoute) {
        navigate(route) {
            // Pop everything up to, and including, the current destination off
            // the back stack.
            // Then navigate to the route destination.
            popUpTo(requireNotNull(currentDestination?.route)) {
                inclusive = true
            }
        }
    }
}

/**
 * Pushes the given destination screen onto the navigation stack. The state of the previous screen
 * is saved and added to the backstack.
 *
 * This function does nothing if the destination's route type is the same as the current screen's.
 *
 * @param route The [Destination] to be pushed onto the navigation stack.
 */
fun <T : Destination> NavController.navigatePush(route: T) {
    val currentDestination = currentBackStackEntry?.destination
    val isCurrentRoute = currentDestination?.hasRoute(route::class) == true

    if (!isCurrentRoute) {
        navigate(route)
    }
}

/**
 * Pop everything off to the start Destination (primary tab) before navigating to the destination [route].
 *
 * This should be used when navigating between tabs on the dashboard. It ensures that
 * all destinations would have a back reference to the start destination ensuring that a back press navigation
 * from the root of any tab navigates to the primary tab.
 *
 * All destinations that are popped will have their state saved, and the target destination's state
 * will be restored (if available) when performing navigation.
 *
 * @param resetState Clears any saved state associated with this [route] before navigation.
 *
 */
fun <T : Destination> NavController.navigateToTab(route: T, resetState: Boolean = false) {
    if (resetState) clearBackStack(route)

    // Navigate to tab route
    navigate(route) {
        popUpTo(requireNotNull(graph.findStartDestination().route)) {
            this.saveState = true
        }
        // Avoid multiple copies of the same destination when
        // re-selecting the same item
        this.launchSingleTop = true
        // Restore state when re-selecting a previously selected item
        this.restoreState = true
    }
}

/**
 * Returns route as an object of type [T]
 *
 * Extrapolates arguments from [NavBackStackEntry.arguments] and recreates object [T]
 *
 * @param [T] the entry's [NavDestination.route] as a [KClass]
 * @return A new instance of this entry's [NavDestination.route] as an object of type [T]
 */
@OptIn(InternalSerializationApi::class)
fun <T : Any> NavBackStackEntry.toRoute(kClass: KClass<out T>): T {
    val bundle = arguments ?: Bundle()
    val typeMap = destination.arguments.mapValues { it.value.type }
    return kClass.serializer().decodeArguments(bundle, typeMap)
}
