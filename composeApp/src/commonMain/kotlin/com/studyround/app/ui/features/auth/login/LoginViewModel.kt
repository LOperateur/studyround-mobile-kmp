package com.studyround.app.ui.features.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import cafe.adriel.voyager.core.model.screenModelScope
import com.studyround.app.auth.model.EmailAuthProviderType
import com.studyround.app.auth.model.GoogleAuthProviderType
import com.studyround.app.auth.session.SessionManager
import com.studyround.app.platform.ui.PlatformContext
import com.studyround.app.repository.login.LoginRepository
import com.studyround.app.service.data.resource.Resource
import com.studyround.app.service.data.resource.windowedLoadDebounce
import com.studyround.app.ui.utils.isValidEmail
import com.studyround.app.ui.utils.isValidUsername
import com.studyround.app.ui.viewmodel.UdfViewModel
import com.studyround.app.ui.viewmodel.WithEffects
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
                        screenModelScope.launch { generateOtp() }
                    } else {
                        _viewEffects.trySend(ShowAlert("Please accept the Terms of Use"))
                    }
                }
            }

            is GoogleLoginClicked -> {
                screenModelScope.launch { loginGoogle(event.context, false) }
            }

            is GoogleSignupClicked -> {
                if (_viewState.value.termsAccepted) {
                    screenModelScope.launch { loginGoogle(event.context, true) }
                } else {
                    _viewEffects.trySend(ShowAlert("Please accept the Terms of Use"))
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
                _viewState.update { state -> state.copy(emailUsernameError = "Email should not be blank") }
            } else if (!emailUsername.isValidEmail()) {
                _viewState.update { state -> state.copy(emailUsernameError = "Invalid email") }
            } else {
                _viewState.update { state -> state.copy(emailUsernameError = null) }
                return true
            }
            return false

        } else {
            if (emailUsername.isBlank()) {
                _viewState.update { state -> state.copy(emailUsernameError = "Username must not be blank") }
            } else if (!emailUsername.isValidUsername()) {
                _viewState.update { state -> state.copy(emailUsernameError = "Invalid username") }
            } else if (emailUsername.trim().length > 24) {
                _viewState.update { state -> state.copy(emailUsernameError = "Username length is 24 characters max") }
            } else {
                _viewState.update { state -> state.copy(emailUsernameError = null) }
                return true
            }
            return false
        }
    }

    private fun checkPasswordValidity(password: String): Boolean {
        if (password.isEmpty()) {
            _viewState.update { state -> state.copy(passwordError = "Password must not be empty") }
        } else if (password.length < 8) {
            _viewState.update { state -> state.copy(passwordError = "Password must be at least 8 characters") }
        } else {
            _viewState.update { state -> state.copy(passwordError = null) }
            return true
        }
        return false
    }

    private fun checkEmailValidity(email: String): Boolean {
        if (email.isBlank()) {
            _viewState.update { state -> state.copy(emailError = "Email should not be blank") }
        } else if (!email.isValidEmail()) {
            _viewState.update { state -> state.copy(emailError = "Invalid email") }
        } else {
            _viewState.update { state -> state.copy(emailError = null) }
            return true
        }
        return false
    }

    private suspend fun loginEmail(emailUsername: String, password: String) {
        sessionManager.login(
            EmailAuthProviderType(
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
                }
            }
        }
    }

    private suspend fun loginGoogle(context: PlatformContext, isSignup: Boolean) {
        sessionManager.login(
            GoogleAuthProviderType(context)
        ).windowedLoadDebounce().collect {
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
                        state.copy(googleLoginLoading = false, googleSignupLoading = false)
                    }
                }

                is Resource.Error -> {
                    _viewState.update { state ->
                        state.copy(googleLoginLoading = false, googleSignupLoading = false)
                    }
                }
            }
        }
    }

    private suspend fun generateOtp() {
        loginRepository.generateOtp()
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
                    }

                    is Resource.Error -> {
                        _viewState.update { state ->
                            state.copy(signupLoading = false)
                        }
                    }
                }
            }
    }
}
