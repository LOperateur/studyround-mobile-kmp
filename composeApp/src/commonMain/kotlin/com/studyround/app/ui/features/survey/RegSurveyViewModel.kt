package com.studyround.app.ui.features.survey

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.studyround.app.ui.viewmodel.UdfViewModel
import com.studyround.app.ui.viewmodel.WithEffects
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class RegSurveyViewModel : UdfViewModel<RegSurveyViewState, RegSurveyViewEvent>(),
    WithEffects<RegSurveyViewEffect> {

    private val _viewState = MutableStateFlow(RegSurveyViewState())
    override val viewState: StateFlow<RegSurveyViewState>
        get() = _viewState.asStateFlow()

    private val _viewEffects = Channel<RegSurveyViewEffect>(Channel.BUFFERED)
    override val viewEffects: Flow<RegSurveyViewEffect>
        get() = _viewEffects.receiveAsFlow()

    var registerTextFieldState by mutableStateOf(RegSurveyTextFieldState())
        private set

    override fun processEvent(event: RegSurveyViewEvent) {
        when (event) {
            is OccupationDropdownItemSelected -> {
                _viewState.update { it.copy(occupationSelection = event.occupation) }
            }
            is GradeDropdownItemSelected -> {
                _viewState.update { it.copy(gradeSelection = event.grade) }
            }
            else -> {
                // do nothing
            }
        }
    }

}
