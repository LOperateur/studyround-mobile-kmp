package com.studyround.app.ui.main

import cafe.adriel.voyager.core.model.screenModelScope
import com.studyround.app.auth.session.SessionManager
import com.studyround.app.platform.utils.Platform
import com.studyround.app.storage.AppPreferences
import com.studyround.app.ui.viewmodel.UdfViewModel
import com.studyround.app.ui.viewmodel.WithEffects
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RootViewModel(
    private val sessionManager: SessionManager,
    private val appPreferences: AppPreferences,
    private val platform: Platform,
) : UdfViewModel<RootViewState, RootViewEvent>(), WithEffects<RootViewEffect> {

    private val _viewState = MutableStateFlow(RootViewState())
    override val viewState: StateFlow<RootViewState>
        get() = _viewState.asStateFlow()

    private val _viewEffects = Channel<RootViewEffect>(Channel.BUFFERED)
    override val viewEffects: Flow<RootViewEffect> = _viewEffects.receiveAsFlow()

    init {
        screenModelScope.launch {
            delay(platform.splashScreenDelay)
            sessionManager.isSignedIn.collect { signedIn ->
                if (signedIn) {
                    signedInNavigationPath()
                } else {
                    signedOutNavigationPath()
                }
            }
        }
    }

    override fun processEvent(event: RootViewEvent) {}

    private fun signedInNavigationPath() {
        screenModelScope.launch {
            if (appPreferences.shouldDisplaySurveyScreen) {
                _viewEffects.send(Navigate(RootDestination.RegSurvey, true))
            } else {
                _viewEffects.send(Navigate(RootDestination.Home, true))
            }
        }
    }

    private fun signedOutNavigationPath() {
        screenModelScope.launch {
            if (appPreferences.isCarouselViewed) {
                _viewEffects.send(Navigate(RootDestination.Auth, true))
            } else {
                _viewEffects.send(Navigate(RootDestination.Onboarding, true))
            }
        }
    }
}
