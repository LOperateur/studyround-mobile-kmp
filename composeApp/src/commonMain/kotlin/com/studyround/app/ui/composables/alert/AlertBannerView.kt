package com.studyround.app.ui.composables.alert

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.neumorphic.LightSource
import com.studyround.app.ui.neumorphic.neumorphic
import com.studyround.app.ui.neumorphic.shape.Flat
import com.studyround.app.ui.neumorphic.shape.RoundedCorner
import com.studyround.app.ui.theme.StudyRoundTheme
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.*

@Composable
fun AlertBannerView(vm: AlertBannerViewModel) {
    val viewState by vm.viewState.collectAsState()

    LazyColumn(
        Modifier
            .systemBarsPadding()
            .fillMaxWidth()
            .wrapContentHeight()
            .animateContentSize(),
        userScrollEnabled = false,
    ) {
        items(
            items = viewState.orderedAlerts(),
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
        eventProcessor(AlertAnimatedIn(alertState.id))
    }

    LaunchedEffect(alertState.visible) {
        // Auto-dismiss after a delay when added
        if (alertState.visible) {
            delay(autoDismissDelay)
            eventProcessor(AlertAnimatedOut(alertState.id))
        }
    }

    AnimatedVisibility(
        modifier = Modifier.animateItem(),
        visible = alertState.visible,
        enter = slideInVertically(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessLow,
            ),
        ) { -it },
        exit = scaleOut(animationSpec = tween(easing = EaseInOut))
                + fadeOut(animationSpec = tween(easing = EaseInOut)),
    ) {
        AlertBanner(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            id = alertState.id,
            eventProcessor = eventProcessor,
            message = alertState.message,
            type = alertState.type,
        ) {
            eventProcessor(AlertAnimatedOut(alertState.id))
        }
    }
}

@Composable
fun AlertBanner(
    modifier: Modifier = Modifier,
    id: String,
    eventProcessor: (AlertBannerViewEvent) -> Unit,
    message: String,
    type: AlertBannerType,
    onAlertColor: Color = StudyRoundTheme.colors.tone1,
    onDismiss: () -> Unit,
) {
    DisposableEffect(Unit) {
        onDispose { eventProcessor(AlertDismissed(id)) }
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier.widthIn(max = 600.dp).alertShadow().background(
                color = type.color,
                shape = RoundedCornerShape(12.dp),
            )
        ) {
            Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)) {
                Icon(
                    modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
                    painter = painterResource(type.icon),
                    tint = onAlertColor,
                    contentDescription = "",
                )

                Text(
                    modifier = Modifier.padding(start = 8.dp, end = 20.dp)
                        .align(Alignment.CenterVertically),
                    text = message,
                    color = onAlertColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = StudyRoundTheme.typography.bodySmall,
                )
            }

            IconButton(
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).size(16.dp),
                onClick = onDismiss,
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_close),
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
