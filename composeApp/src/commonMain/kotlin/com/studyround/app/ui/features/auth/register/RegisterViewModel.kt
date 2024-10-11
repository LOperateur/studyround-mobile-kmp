package com.studyround.app.ui.features.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.core.bundle.Bundle
import androidx.lifecycle.viewModelScope
import com.studyround.app.data.auth.model.EmailAuthType
import com.studyround.app.data.auth.session.SessionManager
import com.studyround.app.data.error.renderedErrorMessage
import com.studyround.app.data.resource.Resource
import com.studyround.app.data.resource.windowedLoadDebounce
import com.studyround.app.ui.composables.alert.AlertBannerType
import com.studyround.app.ui.features.auth.AuthDestination
import com.studyround.app.ui.viewmodel.UdfViewModel
import com.studyround.app.ui.viewmodel.WithEffects
import com.studyround.app.utils.AppString
import com.studyround.app.utils.AppStrings
import com.studyround.app.utils.isValidUsername
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val sessionManager: SessionManager,
    args: Bundle,
) : UdfViewModel<RegisterViewState, RegisterViewEvent>(), WithEffects<RegisterViewEffect> {

    private val _viewState = MutableStateFlow(RegisterViewState())
    override val viewState: StateFlow<RegisterViewState>
        get() = _viewState.asStateFlow()

    private val _viewEffects = Channel<RegisterViewEffect>(Channel.BUFFERED)
    override val viewEffects: Flow<RegisterViewEffect>
        get() = _viewEffects.receiveAsFlow()

    var registerTextFieldState by mutableStateOf(RegisterTextFieldState())
        private set

    private var hasAttemptedRegistration = false
    private var passToken: String? = null

    init {
        initArgs(passToken = args.getString(AuthDestination.Register.PASS_TOKEN))
        displayLocalValidationErrors()
    }

    private fun initArgs(passToken: String? = null) {
        this.passToken = passToken
    }

    override fun processEvent(event: RegisterViewEvent) {
        when (event) {
            is UsernameTextChanged -> {
                registerTextFieldState =
                    registerTextFieldState.copy(usernameText = event.username)
            }

            is PasswordTextChanged -> {
                registerTextFieldState =
                    registerTextFieldState.copy(passwordText = event.password)
            }

            is PasswordConfirmationTextChanged -> {
                registerTextFieldState =
                    registerTextFieldState.copy(passwordConfirmationText = event.password)
            }

            RegisterClicked -> {
                hasAttemptedRegistration = true
                val isUsernameValid = checkUsernameValidity(registerTextFieldState.usernameText)
                val isPasswordValid = checkPasswordValidity(registerTextFieldState.passwordText)
                val isPasswordConfirmationValid =
                    checkPasswordConfirmationValidity(registerTextFieldState.passwordConfirmationText)

                if (isUsernameValid && isPasswordValid && isPasswordConfirmationValid) {
                    viewModelScope.launch {
                        passToken?.let {
                            register(
                                username = registerTextFieldState.usernameText,
                                password = registerTextFieldState.passwordText,
                                passToken = it,
                            )
                        } ?: run {
                            _viewEffects.send(
                                ShowAlert(
                                    message = AppString(AppStrings.SOMETHING_WRONG),
                                    type = AlertBannerType.Error,
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun displayLocalValidationErrors() {
        viewModelScope.launch {
            snapshotFlow { registerTextFieldState }
                .collect {
                    with(it) {
                        if (hasAttemptedRegistration) {
                            checkUsernameValidity(usernameText)
                            checkPasswordValidity(passwordText)
                            checkPasswordConfirmationValidity(passwordConfirmationText)
                        }
                    }
                }
        }
    }

    private fun checkUsernameValidity(username: String): Boolean {
        if (username.isBlank()) {
            _viewState.update { state -> state.copy(usernameError = AppString(AppStrings.BLANK_USERNAME_ERROR)) }
        } else if (!username.isValidUsername()) {
            _viewState.update { state -> state.copy(usernameError = AppString(AppStrings.INVALID_USERNAME_ERROR)) }
        } else if (username.trim().length < 3) {
            _viewState.update { state -> state.copy(usernameError = AppString(AppStrings.LONG_USERNAME_ERROR)) }
        } else if (username.trim().length > 24) {
            _viewState.update { state -> state.copy(usernameError = AppString(AppStrings.LONG_USERNAME_ERROR)) }
        } else {
            _viewState.update { state -> state.copy(usernameError = null) }
            return true
        }
        return false
    }

    private fun checkPasswordValidity(password: String): Boolean {
        if (password.isEmpty()) {
            _viewState.update { state -> state.copy(passwordError = AppString(AppStrings.EMPTY_PASSWORD_ERROR)) }
        } else if (password.length < 8) {
            _viewState.update { state -> state.copy(passwordError = AppString(AppStrings.SHORT_PASSWORD_ERROR)) }
        } else {
            _viewState.update { state -> state.copy(passwordError = null) }
            return true
        }
        return false
    }

    private fun checkPasswordConfirmationValidity(password: String): Boolean {
        if (password.isEmpty()) {
            _viewState.update { state -> state.copy(passwordConfirmationError = AppString(AppStrings.EMPTY_PASSWORD_ERROR)) }
        } else if (password != registerTextFieldState.passwordText) {
            _viewState.update { state -> state.copy(passwordConfirmationError = AppString(AppStrings.NO_MATCH_PASSWORD_ERROR)) }
        } else {
            _viewState.update { state -> state.copy(passwordConfirmationError = null) }
            return true
        }
        return false
    }

    private suspend fun register(username: String, password: String, passToken: String) {
        sessionManager.signUp(
            EmailAuthType(
                userIdentity = username,
                password = password,
                passToken = passToken,
            )
        ).windowedLoadDebounce().collect {
            when (it) {
                is Resource.Loading -> {
                    _viewState.update { state ->
                        state.copy(registrationLoading = true)
                    }
                }

                is Resource.Success -> {
                    _viewState.update { state ->
                        state.copy(registrationLoading = false)
                    }
                    _viewEffects.send(
                        ShowAlert(
                            message = AppString.textOrSuccess(it.message),
                            type = AlertBannerType.Success,
                        )
                    )
                }

                is Resource.Error -> {
                    _viewState.update { state ->
                        state.copy(registrationLoading = false)
                    }
                    _viewEffects.send(
                        ShowAlert(
                            message = it.cause.renderedErrorMessage(),
                            type = AlertBannerType.Error,
                        )
                    )
                }
            }
        }
    }
}
