package com.studyround.app.ui.features.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import cafe.adriel.voyager.core.model.screenModelScope
import com.studyround.app.repository.login.LoginRepository
import com.studyround.app.ui.viewmodel.UdfViewModel
import com.studyround.app.ui.viewmodel.WithEffects
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val loginRepository: LoginRepository,
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

    init {
        displayLocalValidationErrors()
    }

    override fun processEvent(event: RegisterViewEvent) {

    }

    private fun displayLocalValidationErrors() {
        screenModelScope.launch {
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

    private fun checkUsernameValidity(usernameText: String) {

    }

    private fun checkPasswordValidity(passwordText: String) {

    }

    private fun checkPasswordConfirmationValidity(passwordConfirmationText: String) {

    }
}
