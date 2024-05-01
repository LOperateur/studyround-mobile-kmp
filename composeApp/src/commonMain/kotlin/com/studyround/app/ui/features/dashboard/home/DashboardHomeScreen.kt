package com.studyround.app.ui.features.dashboard.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.studyround.app.ui.composables.common.StudyRoundTextLogo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.home
import studyround.composeapp.generated.resources.ic_error
import studyround.composeapp.generated.resources.ic_home
import studyround.composeapp.generated.resources.other

class DashboardHomeScreen: Tab {

    @Composable
    override fun Content() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            StudyRoundTextLogo()
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.home)
            val icon = painterResource(Res.drawable.ic_home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}
