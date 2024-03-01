package com.studyround.app.ui.composables.alert

sealed interface AlertBannerViewEvent

data class AlertShown(
    val id: String,
    val message: String,
    val type: AlertBannerType,
) : AlertBannerViewEvent

data class AlertAnimatedIn(val id: String) : AlertBannerViewEvent

data class AlertAnimatedOut(val id: String) : AlertBannerViewEvent

data class AlertDismissed(val id: String) : AlertBannerViewEvent
