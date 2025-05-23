package com.studyround.app.ui.features.survey

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.studyround.app.data.error.renderedErrorMessage
import com.studyround.app.data.repository.survey.RegSurveyRepository
import com.studyround.app.data.resource.Resource
import com.studyround.app.data.resource.windowedLoadDebounce
import com.studyround.app.ui.composables.alert.AlertBannerType
import com.studyround.app.ui.viewmodel.UdfViewModel
import com.studyround.app.ui.viewmodel.WithEffects
import com.studyround.app.utils.AppString
import com.studyround.app.utils.AppStrings
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegSurveyViewModel(
    private val regSurveyRepository: RegSurveyRepository,
) : UdfViewModel<RegSurveyViewState, RegSurveyViewEvent>(), WithEffects<RegSurveyViewEffect> {

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

            is JobTitleTextChanged -> {
                registerTextFieldState = registerTextFieldState.copy(professionText = event.title)
            }

            is AwarenessSourceChanged -> {
                _viewState.update { it.copy(awarenessSource = event.source) }
            }

            SubmitButtonClicked -> {
                val occupation = _viewState.value.occupationSelection?.value
                val suffix = if (_viewState.value.isStudentSelection)
                    _viewState.value.gradeSelection?.value
                else
                    registerTextFieldState.professionText

                val source = _viewState.value.awarenessSource

                if (occupation == null) {
                    _viewEffects.trySend(
                        ShowAlert(
                            message = AppString(AppStrings.NO_OCCUPATION_ERROR),
                            type = AlertBannerType.Error,
                        )
                    )
                    return
                }

                if (suffix.isNullOrBlank() || source == null) {
                    _viewEffects.trySend(
                        ShowAlert(
                            message = AppString(AppStrings.GENERIC_BLANK_DATA_ERROR),
                            type = AlertBannerType.Error,
                        )
                    )
                    return
                }

                viewModelScope.launch {
                    submitSurvey(
                        occupation = occupation,
                        suffix = suffix,
                        source = source,
                    )
                }
            }
        }
    }

    private suspend fun submitSurvey(occupation: String, suffix: String, source: String) {
        regSurveyRepository.submitSurvey(occupation, suffix, source)
            .windowedLoadDebounce().collect {
                when (it) {
                    is Resource.Loading -> {
                        _viewState.update { state -> state.copy(submissionLoading = true) }
                    }

                    is Resource.Success -> {
                        _viewState.update { state -> state.copy(submissionLoading = false) }
                        _viewEffects.send(
                            ShowAlert(
                                message = AppString.textOrSuccess(it.message),
                                type = AlertBannerType.Success,
                            )
                        )
                        _viewEffects.send(NavigateToMain)
                    }

                    is Resource.Error -> {
                        _viewState.update { state -> state.copy(submissionLoading = false) }
                        _viewEffects.send(
                            ShowAlert(
                                message = it.cause.renderedErrorMessage(),
                                type = AlertBannerType.Error,
                            )
                        )
                    }
                }
            }
    }

}
