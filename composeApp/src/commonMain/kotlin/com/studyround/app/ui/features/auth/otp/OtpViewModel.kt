package com.studyround.app.ui.features.auth.otp

import cafe.adriel.voyager.core.model.screenModelScope
import com.studyround.app.data.remote.request.AuthType
import com.studyround.app.repository.login.LoginRepository
import com.studyround.app.service.data.resource.Resource
import com.studyround.app.service.data.resource.windowedLoadDebounce
import com.studyround.app.ui.features.auth.AuthDestination
import com.studyround.app.ui.viewmodel.UdfViewModel
import com.studyround.app.ui.viewmodel.WithEffects
import com.studyround.app.utils.AppString
import com.studyround.app.utils.AppStrings
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class OtpViewModel(
    private val loginRepository: LoginRepository,
) : UdfViewModel<OtpViewState, OtpViewEvent>(), WithEffects<OtpViewEffect> {

    private val _viewState = MutableStateFlow(OtpViewState())
    override val viewState: StateFlow<OtpViewState>
        get() = _viewState.asStateFlow()

    private val _viewEffects = Channel<OtpViewEffect>(Channel.BUFFERED)
    override val viewEffects: Flow<OtpViewEffect> = _viewEffects.receiveAsFlow()

    private var otpId: Int? = null
    private var email: String? = null

    init {
        screenModelScope.launch {
            while (true) {
                delay(1.seconds)
                val secondsLeft = viewState.value.resendOtpWaitSeconds
                _viewState.update { it.copy(resendOtpWaitSeconds = secondsLeft - 1) }

                if (secondsLeft - 1 == 0) {
                    cancel()
                }
            }
        }
    }

    fun initArgs(otpId: Int?, isForgotPassword: Boolean?, email: String? = null) {
        this.otpId = otpId
        this.email = email
        _viewState.update { it.copy(isForgotPassword = isForgotPassword ?: false) }
    }

    override fun processEvent(event: OtpViewEvent) {
        when (event) {
            is OtpTextChanged -> {
                _viewState.update { it.copy(otpText = event.otp) }
            }

            OtpSubmitted -> {
                if (viewState.value.otpText.filter { it.isDigit() }.length < 4) {
                    screenModelScope.launch {
                        _viewEffects.send(ShowAlert(AppString(AppStrings.OTP_LIMIT_ERROR)))
                    }
                } else {
                    // Verify and Navigate
                    screenModelScope.launch {
                        otpId?.let {
                            validateOtp(it, viewState.value.otpText)
                        } ?: run {
                            _viewEffects.send(
                                ShowAlert(message = AppString(AppStrings.SOMETHING_WRONG))
                            )
                        }
                    }
                }
            }

            ResendOtpClicked -> {
                screenModelScope.launch {
                    email?.let {
                        resendOtp(
                            email = it,
                            authType = if (viewState.value.isForgotPassword)
                                AuthType.RESET_PASSWORD
                            else
                                AuthType.VERIFY_EMAIL,
                        )
                    } ?: run {
                        _viewEffects.send(
                            ShowAlert(message = AppString(AppStrings.SOMETHING_WRONG))
                        )
                    }
                }
            }
        }
    }

    private suspend fun validateOtp(otpId: Int, otp: String) {
        loginRepository.validateOtp(otpId, otp)
            .windowedLoadDebounce().collect {
                when (it) {
                    is Resource.Loading -> {
                        _viewState.update { state ->
                            state.copy(otpValidationLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _viewState.update { state ->
                            state.copy(otpValidationLoading = false)
                        }
                        _viewEffects.send(
                            ShowAlert(message = AppString.textOrSuccess(it.message))
                        )
                        //it.data.passToken
                        _viewEffects.send(
                            Navigate(AuthDestination.Signup, true)
                        )
                    }

                    is Resource.Error -> {
                        _viewState.update { state ->
                            state.copy(otpValidationLoading = false)
                        }
                        _viewEffects.send(
                            ShowAlert(AppString.textOrError(it.cause.message))
                        )
                    }
                }
            }
    }

    private suspend fun resendOtp(email: String, authType: AuthType) {
        loginRepository.generateOtp(email, authType, true)
            .windowedLoadDebounce().collect {
                when (it) {
                    is Resource.Loading -> {
                        _viewState.update { state ->
                            state.copy(hasResentOtp = true)
                        }
                    }

                    is Resource.Success -> {
                        _viewState.update { state ->
                            state.copy(hasResentOtp = true)
                        }
                        _viewEffects.send(
                            ShowAlert(
                                message = AppString(it.message, AppStrings.OTP_SENT_ALERT),
                            )
                        )
                    }

                    is Resource.Error -> {
                        _viewState.update { state ->
                            state.copy(hasResentOtp = false)
                        }
                        _viewEffects.send(
                            ShowAlert(
                                AppString.textOrError(it.cause.message)
                            )
                        )
                    }
                }
            }
    }
}
