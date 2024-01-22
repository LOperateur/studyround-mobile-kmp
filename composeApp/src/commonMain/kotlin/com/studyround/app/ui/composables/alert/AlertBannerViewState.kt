package com.studyround.app.ui.composables.alert

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap

data class AlertBannerViewState(
    val alerts: SnapshotStateMap<String, AlertBannerState> = mutableStateMapOf()
) {
    fun orderedAlerts(): List<AlertBannerState> {
        return alerts.toList().sortedByDescending { it.first }.map { it.second }
    }
}

data class AlertBannerState(
    val id: String,
    val message: String = "",
    val visible: Boolean = false,
)
