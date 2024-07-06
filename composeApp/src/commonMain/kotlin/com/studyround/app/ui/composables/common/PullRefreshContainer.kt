package com.studyround.app.ui.composables.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.studyround.app.ui.theme.StudyRoundTheme

/**
 * Composable that wraps the given content in a pull to refresh container.
 *
 * @param isRefreshing - if is refreshing on progress
 * @param onRefreshTriggered - call back for when pull to refresh is triggered
 * @param content - Composable content to be displayed in the pull to refresh view
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshContainer(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefreshTriggered: () -> Unit,
    content: @Composable () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { onRefreshTriggered() },
    )

    Box(modifier = modifier.pullRefresh(pullRefreshState).fillMaxSize()) {
        content()

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = StudyRoundTheme.colors.deviation_tone1_primary1,
            contentColor = StudyRoundTheme.colors.deviation_primary1_white,
            refreshing = isRefreshing,
            state = pullRefreshState,
        )
    }
}
