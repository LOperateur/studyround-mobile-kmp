package com.studyround.app.ui.composables.alert

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalInspectionMode
import kotlinx.datetime.Clock

class AlertManager(val processEvent: (AlertBannerViewEvent) -> Unit) {
    fun show(message: String, type: AlertBannerType) {
        processEvent(
            AlertShown(
                id = Clock.System.now().toEpochMilliseconds().toString(),
                message = message,
                type = type,
            )
        )
    }
}

object LocalAlertManager {
    private val LocalAlertManager = compositionLocalOf<AlertManager> {
        error("LocalAlertManager not set")
    }

    val current: AlertManager
        @Composable get() = if (LocalInspectionMode.current) {
            AlertManager {}
        } else {
            LocalAlertManager.current
        }

    infix fun provides(
        alertManager: AlertManager,
    ): ProvidedValue<AlertManager> {
        return LocalAlertManager.provides(alertManager)
    }
}
