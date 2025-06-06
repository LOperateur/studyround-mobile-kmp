package com.studyround.app.ui.features.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.studyround.app.platform.ui.DynamicSystemBarColors
import com.studyround.app.platform.ui.splashAnimationAlpha
import com.studyround.app.ui.theme.StudyRoundTheme
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import org.jetbrains.compose.resources.painterResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.studyround_logo

@Composable
fun SplashScreen(onSplashRemoved: () -> Unit) {
    DynamicSystemBarColors(
        dynamicStatusBarColor = StudyRoundTheme.colors.deviation_primary3_primary1,
        dynamicNavBarColor = StudyRoundTheme.colors.deviation_primary3_primary1,
        statusBarColor = StudyRoundTheme.colors.deviation_primary3_primary0,
        navBarColor = StudyRoundTheme.colors.deviation_primary3_primary0,
    )

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = StudyRoundTheme.colors.deviation_primary3_primary1),
        contentAlignment = Alignment.Center,
    ) {
        KamelImage(
            modifier = Modifier.size(138.dp).alpha(splashAnimationAlpha()),
            resource = Resource.Success(painterResource(Res.drawable.studyround_logo)),
            contentDescription = "Logo",
        )
    }

    DisposableEffect(Unit) {
        onDispose { onSplashRemoved() }
    }
}
