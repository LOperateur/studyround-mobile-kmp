package com.studyround.app.ui.features.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.studyround.app.MR
import com.studyround.app.platform.ui.DynamicSystemBarColors
import com.studyround.app.ui.navigation.PlaceholderScreen
import com.studyround.app.ui.theme.StudyRoundTheme
import dev.icerock.moko.resources.compose.painterResource
import io.kamel.core.Resource
import io.kamel.image.KamelImage

class SplashScreen : PlaceholderScreen() {
    @Composable
    override fun Content() {
        DynamicSystemBarColors(
            statusBarColor = StudyRoundTheme.colors.deviation_primary3_primary0,
            navBarColor = StudyRoundTheme.colors.deviation_primary3_primary0,
            dynamicStatusBarColor = StudyRoundTheme.colors.deviation_primary3_primary1,
            dynamicNavBarColor = StudyRoundTheme.colors.deviation_primary3_primary1,
        )

        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = StudyRoundTheme.colors.deviation_primary3_primary1),
            contentAlignment = Alignment.Center,
        ) {
            KamelImage(
                modifier = Modifier.size(144.dp),
                resource = Resource.Success(painterResource(MR.images.studyround_logo)),
                contentDescription = "Logo",
            )
        }
    }
}
