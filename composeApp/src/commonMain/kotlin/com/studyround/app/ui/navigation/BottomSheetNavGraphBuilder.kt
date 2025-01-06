package com.studyround.app.ui.navigation

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDestinationBuilder
import androidx.navigation.NavDestinationDsl
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.get
import kotlin.jvm.JvmSuppressWildcards
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * Add the [content] [Composable] as bottom sheet content to the [NavGraphBuilder]
 *
 * @param T route from a [KClass] for the destination
 * @param typeMap map of destination arguments' kotlin type [KType] to its respective custom
 *   [NavType]. May be empty if [T] does not use custom NavTypes.
 * @param arguments list of arguments to associate with destination
 * @param deepLinks list of deep links to associate with the destinations
 * @param content the sheet content at the given destination
 */
inline fun <reified T : Any> NavGraphBuilder.bottomSheet(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline content: @Composable ColumnScope.(backstackEntry: NavBackStackEntry) -> Unit
) {
    destination(
        BottomSheetNavigatorDestinationBuilder(
            provider[BottomSheetNavigator::class],
            T::class,
            typeMap,
            content
        ).apply {
            arguments.fastForEach { (argumentName, argument) ->
                argument(argumentName, argument)
            }
            deepLinks.fastForEach { deepLink -> deepLink(deepLink) }
        }
    )
}

/**
 * DSL for constructing a new [BottomSheetNavigator.Destination]
 *
 * @param navigator navigator used to create the destination
 * @param route the destination's unique route from a [KClass]
 * @param typeMap map of destination arguments' kotlin type [KType] to its respective custom
 *   [NavType]. May be empty if [route] does not use custom NavTypes.
 * @param content composable for the destination
 */
@NavDestinationDsl
class BottomSheetNavigatorDestinationBuilder(
    navigator: BottomSheetNavigator,
    route: KClass<*>,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>>,
    private val content: @Composable ColumnScope.(NavBackStackEntry) -> Unit
) : NavDestinationBuilder<BottomSheetNavigator.Destination>(navigator, route, typeMap) {

    private val bottomSheetNavigator: BottomSheetNavigator = navigator

    override fun instantiateDestination(): BottomSheetNavigator.Destination {
        return BottomSheetNavigator.Destination(bottomSheetNavigator, content)
    }
}
