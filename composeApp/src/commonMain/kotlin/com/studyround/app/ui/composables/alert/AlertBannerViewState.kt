package com.studyround.app.ui.composables.alert

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class AlertBannerViewState(
    val alerts: SnapshotStateList<AlertBannerState> = mutableStateListOf()
)

data class AlertBannerState(
    val id: String,
    val message: String = "",
    val visible: Boolean = true,
)
