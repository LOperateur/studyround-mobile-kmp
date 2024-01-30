package com.studyround.app.ui.features.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import cafe.adriel.voyager.core.model.screenModelScope
import com.studyround.app.auth.model.EmailAuthType
import com.studyround.app.auth.model.GoogleAuthType
import com.studyround.app.auth.session.SessionManager
import com.studyround.app.data.remote.request.AuthType
import com.studyround.app.platform.ui.PlatformContext
import com.studyround.app.repository.login.LoginRepository
import com.studyround.app.service.data.resource.Resource
import com.studyround.app.service.data.resource.windowedLoadDebounce
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sessionManager: SessionManager,
    private val loginRepository: LoginRepository,
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
                    screenModelScope.launch {
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
                        screenModelScope.launch {
                            generateOtp(
                                email = loginTextFieldState.emailText,
                                authType = AuthType.VERIFY_EMAIL,
                                resend = false,
                            )
                        }
                    } else {
                        _viewEffects.trySend(ShowAlert(AppString(AppStrings.ACCEPT_T_AND_C_ERROR)))
                    }
                }
            }

            is GoogleLoginClicked -> {
                screenModelScope.launch { launchGoogleOauth(event.context, false) }
            }

            is GoogleSignupClicked -> {
                if (_viewState.value.termsAccepted) {
                    screenModelScope.launch { launchGoogleOauth(event.context, true) }
                } else {
                    _viewEffects.trySend(ShowAlert(AppString(AppStrings.ACCEPT_T_AND_C_ERROR)))
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
        screenModelScope.launch {
            snapshotFlow { loginTextFieldState }
                .distinctUntilChanged()
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
                    _viewEffects.send(ShowAlert(AppString.textOrError(it.cause.message)))
                }
            }
        }
    }

    private suspend fun launchGoogleOauth(context: PlatformContext, isSignup: Boolean) {
        sessionManager.launchGoogleOauth(isSignup, context)
            .windowedLoadDebounce().collect {
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
                        loginGoogle(it.data)
                    }

                    is Resource.Error -> {
                        _viewState.update { state ->
                            state.copy(googleLoginLoading = false, googleSignupLoading = false)
                        }
                        _viewEffects.send(ShowAlert(AppString.textOrError(it.cause.message)))
                    }
                }
            }
    }

    private suspend fun loginGoogle(idToken: String) {
        sessionManager.login(GoogleAuthType(idToken)).collect {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _viewState.update { state ->
                        state.copy(
                            googleLoginLoading = false,
                            googleSignupLoading = false,
                        )
                    }
                    _viewEffects.send(
                        ShowAlert(AppString("Welcome ${it.data.id}: ${it.data.email}"))
                    )
                }

                is Resource.Error -> {
                    _viewState.update { state ->
                        state.copy(googleLoginLoading = false, googleSignupLoading = false)
                    }
                    _viewEffects.send(ShowAlert(AppString.textOrError(it.cause.message)))
                }
            }
        }
    }

    private suspend fun generateOtp(email: String, authType: AuthType, resend: Boolean) {
        loginRepository.generateOtp(email, authType, resend)
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
                        _viewEffects.send(ShowAlert(AppString.textOrSuccess(it.message)))
                    }

                    is Resource.Error -> {
                        _viewState.update { state ->
                            state.copy(signupLoading = false)
                        }
                        _viewEffects.send(ShowAlert(AppString.textOrError(it.cause.message)))
                    }
                }
            }
    }
}
