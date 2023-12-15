package com.studyround.app.ui.main

import com.studyround.app.ui.viewmodel.UdfViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RootViewModel : UdfViewModel<RootViewState, RootViewEvent>() {

    private val _viewState = MutableStateFlow(RootViewState())
    override val viewState: StateFlow<RootViewState>
        get() = _viewState

    override fun processEvent(event: RootViewEvent) {}
}
