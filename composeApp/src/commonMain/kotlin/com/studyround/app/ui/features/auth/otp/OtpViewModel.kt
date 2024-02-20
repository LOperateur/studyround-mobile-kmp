package com.studyround.app.ui.features.auth.otp

import com.studyround.app.ui.viewmodel.UdfViewModel
import com.studyround.app.ui.viewmodel.WithEffects
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class OtpViewModel: UdfViewModel<OtpViewState, OtpViewEvent>(), WithEffects<OtpViewEffect> {

    private val _viewState = MutableStateFlow(OtpViewState())
    override val viewState: StateFlow<OtpViewState>
        get() = _viewState.asStateFlow()

    override fun processEvent(event: OtpViewEvent) {

    }

    private val _viewEffects = Channel<OtpViewEffect>(Channel.BUFFERED)
    override val viewEffects: Flow<OtpViewEffect> = _viewEffects.receiveAsFlow()
}
