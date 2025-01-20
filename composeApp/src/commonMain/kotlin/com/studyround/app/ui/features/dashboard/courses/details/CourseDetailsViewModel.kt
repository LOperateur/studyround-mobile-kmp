package com.studyround.app.ui.features.dashboard.courses.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.studyround.app.data.error.renderedErrorMessage
import com.studyround.app.data.repository.course.CoursesRepository
import com.studyround.app.data.resource.Resource
import com.studyround.app.data.resource.windowedLoadDebounce
import com.studyround.app.ui.composables.alert.AlertBannerType
import com.studyround.app.ui.features.dashboard.courses.CoursesBottomSheetDestination
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

class CourseDetailsViewModel(
    private val coursesRepository: CoursesRepository,
    savedStateHandle: SavedStateHandle,
) : UdfViewModel<CourseDetailsViewState, CourseDetailsViewEvent>(), WithEffects<CourseDetailsViewEffect> {

    private val _viewState = MutableStateFlow(CourseDetailsViewState(loading = true))
    override val viewState: StateFlow<CourseDetailsViewState>
        get() = _viewState.asStateFlow()

    private val _viewEffects = Channel<CourseDetailsViewEffect>(Channel.BUFFERED)
    override val viewEffects: Flow<CourseDetailsViewEffect> = _viewEffects.receiveAsFlow()

    init {
        with(savedStateHandle.toRoute<CoursesBottomSheetDestination.CourseDetailsSheet>().courseId) {
            getCourseDetails(this)
        }
    }

    override fun processEvent(event: CourseDetailsViewEvent) {

    }

    private fun getCourseDetails(courseId: Long) {
        viewModelScope.launch {
            coursesRepository.fetchCourseDetails(courseId)
                .windowedLoadDebounce().collect {
                    when (it) {
                        is Resource.Loading -> {
                            _viewState.update { state ->
                                state.copy(
                                    loading = true,
                                    error = false,
                                )
                            }
                        }

                        is Resource.Success -> {
                            _viewState.update { state ->
                                state.copy(
                                    loading = false,
                                    course = it.data,
                                    error = false,
                                )
                            }
                        }

                        is Resource.Error -> {
                            if (viewState.value.course == null) {
                                _viewState.update { state ->
                                    state.copy(
                                        loading = false,
                                        error = true
                                    )
                                }
                            } else {
                                _viewState.update { state ->
                                    state.copy(
                                        loading = false,
                                        error = false,
                                    )
                                }
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
    }
}
