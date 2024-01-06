package com.studyround.app.ui.features.auth.login

import com.studyround.app.ui.viewmodel.UdfViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : UdfViewModel<LoginViewState, LoginViewEvent>() {

    private val _viewState = MutableStateFlow(LoginViewState())
    override val viewState: StateFlow<LoginViewState>
        get() = _viewState

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
        }
    }
}
