package com.studyround.app.ui.features.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import cafe.adriel.voyager.core.model.screenModelScope
import com.studyround.app.ui.viewmodel.UdfViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : UdfViewModel<LoginViewState, LoginViewEvent>() {

    private val _viewState = MutableStateFlow(LoginViewState())
    override val viewState: StateFlow<LoginViewState>
        get() = _viewState

    var loginTextFieldState by mutableStateOf(LoginTextFieldState())
        private set

    init {
        displayLocalValidationErrors()
    }

    override fun processEvent(event: LoginViewEvent) {
        when (event) {
            GoToLoginClicked -> {
                _viewState.update {
                    it.copy(isSignUp = false)
                }
            }

            GoToSignupClicked -> {
                _viewState.update {
                    it.copy(isSignUp = true)
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
        }
    }

    private fun displayLocalValidationErrors() {
        screenModelScope.launch {
            snapshotFlow { loginTextFieldState }
                .distinctUntilChanged()
                .collect {
                    with(it) {
                        // Todo: Validate all fields
                        if (emailUsernameText.contains("%")) {
                            _viewState.update { state -> state.copy(emailUsernameError = "Invalid email") }
                        } else {
                            _viewState.update { state -> state.copy(emailUsernameError = null) }
                        }
                    }
                }
        }
    }
}
