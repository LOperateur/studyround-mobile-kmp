package com.studyround.app.ui.composables.alert

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.studyround.app.MR
import com.studyround.app.ui.neumorphic.LightSource
import com.studyround.app.ui.neumorphic.neumorphic
import com.studyround.app.ui.neumorphic.shape.Flat
import com.studyround.app.ui.neumorphic.shape.RoundedCorner
import com.studyround.app.ui.theme.StudyRoundTheme
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.delay

@Composable
fun AlertBannerView(vm: AlertBannerViewModel) {
    val viewState by vm.viewState.collectAsState()

    LazyColumn(
        Modifier
            .systemBarsPadding()
            .fillMaxWidth()
            .wrapContentHeight(),
        reverseLayout = true,
    ) {
        items(
            items = viewState.alerts,
            key = { alertState -> alertState.id },
        ) {
            AlertBannerWrapper(
                alertState = it,
                eventProcessor = vm::processEvent,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.AlertBannerWrapper(
    alertState: AlertBannerState,
    eventProcessor: (AlertBannerViewEvent) -> Unit,
) {
    val autoDismissDelay = 5_000L // 5s delay

    LaunchedEffect(Unit) {
        // Every time this becomes visible we want to auto-dismiss after a delay
        if (alertState.visible) {
            delay(autoDismissDelay)
            eventProcessor(DismissAlertBanner(alertState.id))
        }
    }

//    AnimatedVisibility(
//        modifier = Modifier,
//        visible = alertState.visible,
//        enter = slideInVertically(
//            animationSpec = spring(
//                dampingRatio = Spring.DampingRatioNoBouncy,
//                stiffness = Spring.StiffnessLow,
//            ),
//        ) { -it },
//        exit = slideOutVertically(
//            animationSpec = spring(
//                dampingRatio = Spring.DampingRatioNoBouncy,
//                stiffness = Spring.StiffnessLow,
//            ),
//        ) { -it },
//    ) {
    AlertBanner(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        message = alertState.message,
    ) {
        eventProcessor(DismissAlertBanner(alertState.id))
    }
//    }
}

@Composable
fun AlertBanner(
    modifier: Modifier = Modifier,
    message: String = "Something went wrong.",
    alertColor: Color = StudyRoundTheme.colors.danger,
    onAlertColor: Color = StudyRoundTheme.colors.tone1,
    onDismiss: () -> Unit,
) {
    Surface(
        modifier = modifier.alertShadow(),
        shape = RoundedCornerShape(12.dp),
        color = alertColor,
    ) {
        Box {
            Row(modifier = Modifier.padding(vertical = 16.dp)) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(imageResource = MR.images.ic_error),
                    tint = onAlertColor,
                    contentDescription = "",
                )

                Text(
                    modifier = Modifier.weight(1f, fill = true),
                    text = message,
                    color = onAlertColor,
                    style = StudyRoundTheme.typography.bodySmall,
                )
            }

            IconButton(
                modifier = Modifier.align(Alignment.TopEnd).padding(4.dp).size(8.dp),
                onClick = onDismiss,
            ) {
                Icon(
                    painter = painterResource(imageResource = MR.images.ic_close),
                    tint = onAlertColor,
                    contentDescription = "Close",
                )
            }
        }
    }
}

@Composable
fun Modifier.alertShadow(): Modifier {
    return neumorphic(
        lightShadowColor = Color.Unspecified,
        darkShadowColor = StudyRoundTheme.colors.shadow,
        lightSource = LightSource.LEFT_TOP,
        shadowElevation = 4.dp,
        shape = Flat(cornerShape = RoundedCorner(12.dp)),
    )
}
