package com.studyround.app.ui.composables.alert

sealed interface AlertBannerViewEvent

data class ShowAlertBanner(val id: String, val message: String): AlertBannerViewEvent

data class DismissAlertBanner(val id: String): AlertBannerViewEvent
