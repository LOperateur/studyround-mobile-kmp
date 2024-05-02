package com.studyround.app.ui.composables.common.appbar

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

enum class AppBarNavDestination {
    PROFILE, RESULTS, FAQS, TRANSACTIONS
}

class AppBarViewModel(
    appPreferences: AppPreferences,
) : UdfViewModel<AppBarViewState, AppBarViewEvent>(), WithEffects<AppBarViewEffect> {

    private val _viewState = MutableStateFlow(AppBarViewState(false, appPreferences.avatarUrl))
    override val viewState: StateFlow<AppBarViewState>
        get() = _viewState.asStateFlow()

    private val _viewEffects = Channel<AppBarViewEffect>(Channel.BUFFERED)
    override val viewEffects: Flow<AppBarViewEffect> = _viewEffects.receiveAsFlow()

    override fun processEvent(event: AppBarViewEvent) {
        when (event) {
            is MenuToggled -> {
                _viewState.update {
                    it.copy(isMenuOpened = event.opened)
                }
            }

            is DarkModeToggled -> {}

            is NavDestinationClicked -> {
                _viewEffects.trySend(RequestNavigate(event.destination))
            }
        }
    }
}
