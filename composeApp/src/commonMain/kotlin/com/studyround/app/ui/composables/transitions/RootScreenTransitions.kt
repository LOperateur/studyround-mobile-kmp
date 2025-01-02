package com.studyround.app.ui.composables.transitions

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.unit.IntOffset
import com.studyround.app.ui.main.SplashMonitor

fun rootScreenSplashEnterTransition(splashMonitor: SplashMonitor): EnterTransition {
    return if (splashMonitor.isSplashScreenShowing) {
        fadeIn(animationSpec = spring(stiffness = Spring.StiffnessMediumLow))
    } else {
        slideInHorizontally(
            spring(
                stiffness = Spring.StiffnessMediumLow,
                visibilityThreshold = IntOffset.VisibilityThreshold,
            )
        ) { it }
    }
}

fun rootScreenSplashExitTransition(splashMonitor: SplashMonitor): ExitTransition {
    return if (splashMonitor.isSplashScreenShowing) {
        ExitTransition.None
    } else {
        slideOutHorizontally(
            spring(
                stiffness = Spring.StiffnessMediumLow,
                visibilityThreshold = IntOffset.VisibilityThreshold,
            )
        ) { -it }
    }
}

val rootScreenPopEnterTransition: EnterTransition =
    slideInHorizontally(
        spring(
            stiffness = Spring.StiffnessMediumLow,
            visibilityThreshold = IntOffset.VisibilityThreshold,
        )
    ) { -it }

val rootScreenPopExitTransition: ExitTransition =
    slideOutHorizontally(
        spring(
            stiffness = Spring.StiffnessMediumLow,
            visibilityThreshold = IntOffset.VisibilityThreshold,
        )
    ) { it }
