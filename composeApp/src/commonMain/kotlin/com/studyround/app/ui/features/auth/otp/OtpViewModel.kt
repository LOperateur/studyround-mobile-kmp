package com.studyround.app.ui.features.auth.otp

import cafe.adriel.voyager.core.model.screenModelScope
import com.studyround.app.ui.viewmodel.UdfViewModel
import com.studyround.app.ui.viewmodel.WithEffects
import com.studyround.app.utils.AppString
import com.studyround.app.utils.AppStrings
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OtpViewModel : UdfViewModel<OtpViewState, OtpViewEvent>(), WithEffects<OtpViewEffect> {

    private val _viewState = MutableStateFlow(OtpViewState())
    override val viewState: StateFlow<OtpViewState>
        get() = _viewState.asStateFlow()

    private val _viewEffects = Channel<OtpViewEffect>(Channel.BUFFERED)
    override val viewEffects: Flow<OtpViewEffect> = _viewEffects.receiveAsFlow()

    fun initPath(isForgotPassword: Boolean) {
        _viewState.update { it.copy(isForgotPassword = isForgotPassword) }
    }

    override fun processEvent(event: OtpViewEvent) {
        when (event) {
            is OtpTextChanged -> {
                _viewState.update { it.copy(otpText = event.otp) }
            }

            OtpSubmitted -> {
                if (viewState.value.otpText.filter { it.isDigit() }.length < 4) {
                    screenModelScope.launch {
                        _viewEffects.send(ShowAlert(AppString(AppStrings.SOMETHING_WRONG)))
                    }
                } else {
                    // Verify and Navigate
                }
            }

            ResendOtpClicked -> {
                // Verify
            }
        }
    }
}
