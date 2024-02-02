package com.studyround.app.ui.composables.transitions

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScreenTransition
import cafe.adriel.voyager.transitions.ScreenTransitionContent
import com.studyround.app.ui.main.RootScreen

@Composable
fun RootScreenSplashTransition(
    navigator: Navigator,
    splashMonitor: RootScreen.SplashMonitor,
    modifier: Modifier = Modifier,
    animationSpecFade: FiniteAnimationSpec<Float> = spring(stiffness = Spring.StiffnessMediumLow),
    animationSpecSlide: FiniteAnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold,
    ),
    content: ScreenTransitionContent = { it.Content() }
) {
    ScreenTransition(
        navigator = navigator,
        modifier = modifier,
        content = content,
        transition = {
            if (splashMonitor.isSplashScreenShowing) {
                fadeIn(animationSpec = animationSpecFade) togetherWith ExitTransition.None
            } else {
                val (initialOffset, targetOffset) = when (navigator.lastEvent) {
                    StackEvent.Pop -> ({ size: Int -> -size }) to ({ size: Int -> size })
                    else -> ({ size: Int -> size }) to ({ size: Int -> -size })
                }
                slideInHorizontally(animationSpecSlide, initialOffset) togetherWith
                        slideOutHorizontally(animationSpecSlide, targetOffset)
            }
        }
    )
}
