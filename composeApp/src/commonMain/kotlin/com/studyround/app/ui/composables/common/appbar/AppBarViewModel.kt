package com.studyround.app.ui.composables.common.appbar

import cafe.adriel.voyager.core.model.screenModelScope
import com.studyround.app.storage.AppPreferences
import com.studyround.app.ui.viewmodel.UdfViewModel
import com.studyround.app.ui.viewmodel.WithEffects
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AppBarNavDestination {
    PROFILE, RESULTS, FAQS, TRANSACTIONS
}

class AppBarViewModel(
    private val appPreferences: AppPreferences,
) : UdfViewModel<AppBarViewState, AppBarViewEvent>(), WithEffects<AppBarViewEffect> {

    private val _viewState = MutableStateFlow(
        AppBarViewState(
            isMenuOpened = false,
            avatarUrl = appPreferences.avatarUrl,
            darkModePreference = appPreferences.darkMode,
        )
    )

    override val viewState: StateFlow<AppBarViewState>
        get() = _viewState.asStateFlow()

    private val _viewEffects = Channel<AppBarViewEffect>(Channel.BUFFERED)
    override val viewEffects: Flow<AppBarViewEffect> = _viewEffects.receiveAsFlow()

    init {
        screenModelScope.launch {
            observeTheme()
        }
    }

    override fun processEvent(event: AppBarViewEvent) {
        when (event) {
            is MenuToggled -> {
                _viewState.update {
                    it.copy(isMenuOpened = event.opened)
                }
            }

            is DarkModeToggled -> {
                appPreferences.setDarkMode(event.isDarkMode)
            }

            is NavDestinationClicked -> {
                _viewEffects.trySend(RequestNavigate(event.destination))
            }
        }
    }

    private suspend fun observeTheme() {
        appPreferences.observeDarkMode().collect {
            _viewState.update { state ->
                state.copy(darkModePreference = it)
            }
        }
    }
}
