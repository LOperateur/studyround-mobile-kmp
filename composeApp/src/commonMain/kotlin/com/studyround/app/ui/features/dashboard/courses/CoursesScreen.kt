package com.studyround.app.ui.features.dashboard.courses

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
import studyround.composeapp.generated.resources.*

class CoursesScreen: Tab {

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
            val title = stringResource(Res.string.courses)
            val icon = painterResource(Res.drawable.ic_menu_book)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}
