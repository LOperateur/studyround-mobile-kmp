package com.studyround.app.ui.features.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.studyround.app.data.auth.model.EmailAuthType
import com.studyround.app.data.auth.model.GoogleAuthType
import com.studyround.app.data.auth.session.SessionManager
import com.studyround.app.data.error.renderedErrorMessage
import com.studyround.app.data.model.remote.request.AuthType
import com.studyround.app.platform.ui.PlatformContext
import com.studyround.app.data.repository.auth.AuthRepository
import com.studyround.app.data.resource.Resource
import com.studyround.app.data.resource.windowedLoadDebounce
import com.studyround.app.ui.composables.alert.AlertBannerType
import com.studyround.app.ui.features.auth.AuthDestination
import com.studyround.app.ui.features.auth.AuthDestination.OTP.Companion.EMAIL
import com.studyround.app.ui.features.auth.AuthDestination.OTP.Companion.FORGOT_PASSWORD
import com.studyround.app.ui.features.auth.AuthDestination.OTP.Companion.OTP_ID
import com.studyround.app.utils.isValidEmail
import com.studyround.app.utils.isValidUsername
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

class LoginViewModel(
    private val sessionManager: SessionManager,
    private val authRepository: AuthRepository,
) : UdfViewModel<LoginViewState, LoginViewEvent>(), WithEffects<LoginViewEffect> {

    private val _viewState = MutableStateFlow(LoginViewState())
    override val viewState: StateFlow<LoginViewState>
        get() = _viewState.asStateFlow()

    private val _viewEffects = Channel<LoginViewEffect>(Channel.BUFFERED)
    override val viewEffects: Flow<LoginViewEffect> = _viewEffects.receiveAsFlow()

    var loginTextFieldState by mutableStateOf(LoginTextFieldState())
        private set

    private var hasAttemptedLogin = false
    private var hasAttemptedSignup = false

    init {
        displayLocalValidationErrors()
    }

    override fun processEvent(event: LoginViewEvent) {
        // Todo: Prevent any other button click event while a request is loading
        when (event) {
            GoToLoginClicked -> {
                _viewState.update {
                    it.copy(isSignup = false)
                }
            }

            GoToSignupClicked -> {
                _viewState.update {
                    it.copy(isSignup = true)
                }
            }

            is EmailUsernameTextChanged -> {
                loginTextFieldState =
                    loginTextFieldState.copy(emailUsernameText = event.emailUsername)
            }

            is PasswordTextChanged -> {
                loginTextFieldState =
                    loginTextFieldState.copy(passwordText = event.password)
            }

            is EmailTextChanged -> {
                loginTextFieldState =
                    loginTextFieldState.copy(emailText = event.email)
            }

            LoginClicked -> {
                hasAttemptedLogin = true
                val idValid = checkEmailUsernameValidity(loginTextFieldState.emailUsernameText)
                val passwordValid = checkPasswordValidity(loginTextFieldState.passwordText)

                if (idValid && passwordValid) {
                    viewModelScope.launch {
                        loginEmail(
                            loginTextFieldState.emailUsernameText,
                            loginTextFieldState.passwordText,
                        )
                    }
                }
            }

            SignupClicked -> {
                hasAttemptedSignup = true
                if (checkEmailValidity(loginTextFieldState.emailText)) {
                    if (_viewState.value.termsAccepted) {
                        viewModelScope.launch {
                            generateOtp(
                                email = loginTextFieldState.emailText,
                                authType = AuthType.VERIFY_EMAIL,
                            )
                        }
                    } else {
                        _viewEffects.trySend(
                            ShowAlert(
                                message = AppString(AppStrings.ACCEPT_T_AND_C_ERROR),
                                type = AlertBannerType.Error,
                            )
                        )
                    }
                }
            }

            is GoogleLoginClicked -> {
                viewModelScope.launch { loginGoogle(event.context, false) }
            }

            is GoogleSignupClicked -> {
                if (_viewState.value.termsAccepted) {
                    viewModelScope.launch { loginGoogle(event.context, true) }
                } else {
                    _viewEffects.trySend(
                        ShowAlert(
                            message = AppString(AppStrings.ACCEPT_T_AND_C_ERROR),
                            type = AlertBannerType.Error,
                        )
                    )
                }
            }

            is TermsToggled -> {
                _viewState.update {
                    it.copy(termsAccepted = event.accepted)
                }
            }
        }
    }

    private fun displayLocalValidationErrors() {
        viewModelScope.launch {
            snapshotFlow { loginTextFieldState }
                .collect {
                    with(it) {
                        if (hasAttemptedLogin) {
                            checkEmailUsernameValidity(emailUsernameText)
                            checkPasswordValidity(passwordText)
                        }
                        if (hasAttemptedSignup) checkEmailValidity(emailText)
                    }
                }
        }
    }

    private fun checkEmailUsernameValidity(emailUsername: String): Boolean {
        if (emailUsername.contains("@")) {
            if (emailUsername.isBlank()) {
                _viewState.update { state -> state.copy(emailUsernameError = AppString(AppStrings.BLANK_EMAIL_ERROR)) }
            } else if (!emailUsername.isValidEmail()) {
                _viewState.update { state -> state.copy(emailUsernameError = AppString(AppStrings.INVALID_EMAIL_ERROR)) }
            } else {
                _viewState.update { state -> state.copy(emailUsernameError = null) }
                return true
            }
            return false

        } else {
            if (emailUsername.isBlank()) {
                _viewState.update { state -> state.copy(emailUsernameError = AppString(AppStrings.BLANK_USERNAME_ERROR)) }
            } else if (!emailUsername.isValidUsername()) {
                _viewState.update { state -> state.copy(emailUsernameError = AppString(AppStrings.INVALID_USERNAME_ERROR)) }
            } else if (emailUsername.trim().length > 24) {
                _viewState.update { state -> state.copy(emailUsernameError = AppString(AppStrings.LONG_USERNAME_ERROR)) }
            } else {
                _viewState.update { state -> state.copy(emailUsernameError = null) }
                return true
            }
            return false
        }
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

    private fun checkEmailValidity(email: String): Boolean {
        if (email.isBlank()) {
            _viewState.update { state -> state.copy(emailError = AppString(AppStrings.BLANK_EMAIL_ERROR)) }
        } else if (!email.isValidEmail()) {
            _viewState.update { state -> state.copy(emailError = AppString(AppStrings.INVALID_EMAIL_ERROR)) }
        } else {
            _viewState.update { state -> state.copy(emailError = null) }
            return true
        }
        return false
    }

    private suspend fun loginEmail(emailUsername: String, password: String) {
        sessionManager.login(
            EmailAuthType(
                userIdentity = emailUsername,
                password = password,
                passToken = "", // Not required for login
            )
        ).windowedLoadDebounce().collect {
            when (it) {
                is Resource.Loading -> {
                    _viewState.update { state ->
                        state.copy(loginLoading = true)
                    }
                }

                is Resource.Success -> {
                    _viewState.update { state ->
                        state.copy(loginLoading = false)
                    }
                }

                is Resource.Error -> {
                    _viewState.update { state ->
                        state.copy(loginLoading = false)
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

    private suspend fun loginGoogle(context: PlatformContext, isSignup: Boolean) {
        sessionManager.login(GoogleAuthType(context)).collect {
            when (it) {
                is Resource.Loading -> {
                    _viewState.update { state ->
                        if (isSignup)
                            state.copy(googleSignupLoading = true)
                        else
                            state.copy(googleLoginLoading = true)
                    }
                }

                is Resource.Success -> {
                    _viewState.update { state ->
                        state.copy(
                            googleLoginLoading = false,
                            googleSignupLoading = false,
                        )
                    }
                }

                is Resource.Error -> {
                    _viewState.update { state ->
                        state.copy(googleLoginLoading = false, googleSignupLoading = false)
                    }

                    val googleAuthError = if (isSignup) AppStrings.GOOGLE_SIGN_UP_ERROR
                    else AppStrings.GOOGLE_SIGN_IN_ERROR

                    _viewEffects.send(
                        ShowAlert(
                            message = AppString(googleAuthError),
                            type = AlertBannerType.Error,
                        )
                    )
                }
            }
        }
    }

    private suspend fun generateOtp(email: String, authType: AuthType) {
        authRepository.generateOtp(email, authType, false)
            .windowedLoadDebounce().collect {
                when (it) {
                    is Resource.Loading -> {
                        _viewState.update { state ->
                            state.copy(signupLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _viewState.update { state ->
                            state.copy(signupLoading = false)
                        }
                        _viewEffects.send(
                            ShowAlert(
                                message = AppString(it.message, AppStrings.OTP_SENT_ALERT),
                                type = AlertBannerType.Success,
                                args = arrayOf(email)
                            )
                        )
                        _viewEffects.send(
                            Navigate(
                                AuthDestination.OTP(
                                    mapOf(
                                        OTP_ID to it.data.otpId,
                                        FORGOT_PASSWORD to false,
                                        EMAIL to email,
                                    )
                                ),
                                false,
                            )
                        )
                    }

                    is Resource.Error -> {
                        _viewState.update { state ->
                            state.copy(signupLoading = false)
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
