package com.studyround.app.ui.composables.alert

import com.studyround.app.ui.viewmodel.UdfViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AlertBannerViewModel : UdfViewModel<AlertBannerViewState, AlertBannerViewEvent>() {

    private var _viewState = MutableStateFlow(AlertBannerViewState())
    override val viewState: StateFlow<AlertBannerViewState>
        get() = _viewState.asStateFlow()

    override fun processEvent(event: AlertBannerViewEvent) {
        when (event) {
            is ShowAlertBanner -> {
                val alert = AlertBannerState(event.id, event.message)
                _viewState.value.alerts.add(alert)
            }

            is DismissAlertBanner -> {
                _viewState.value.alerts.removeAll { it.id == event.id }
            }
        }
    }
}
