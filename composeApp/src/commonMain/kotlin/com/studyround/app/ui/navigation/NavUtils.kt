package com.studyround.app.ui.navigation

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

/**
 * Navigates to the given destination screen. It decides whether to replace the current screen
 * or push a new screen onto the navigation stack based on the type of the last item in the navigator.
 *
 * If the last item in the navigator is a [PlaceholderScreen], it replaces the current screen.
 * Otherwise, it pushes the new destination onto the stack.
 *
 * @param destination The screen to navigate to.
 */
fun Navigator?.navigate(destination: Screen) {
    if (this?.lastItem is PlaceholderScreen) {
        navigateReplace(destination)
    } else {
        navigatePush(destination)
    }
}

/**
 * Replaces the current screen with the given destination screen if the destination's key
 * is different from the last item's key in the navigator (current screen).
 *
 * This function does nothing if the destination's key is the same as the current screen's key.
 *
 * @param destination The screen to replace the current screen with.
 */
fun Navigator?.navigateReplace(destination: Screen) {
    if (destination.key != this?.lastItem?.key)
        this?.replace(destination)
}

/**
 * Pushes the given destination screen onto the navigation stack if the destination's key
 * is different from the last item's key in the navigator (current screen).
 *
 * This function does nothing if the destination's key is the same as the current screen's key.
 *
 * @param destination The screen to be pushed onto the navigation stack.
 */
fun Navigator?.navigatePush(destination: Screen) {
    if (destination.key != this?.lastItem?.key)
        this?.push(destination)
}
