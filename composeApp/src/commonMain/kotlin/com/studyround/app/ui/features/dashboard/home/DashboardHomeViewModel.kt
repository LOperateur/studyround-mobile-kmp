package com.studyround.app.ui.features.dashboard.home

import androidx.lifecycle.viewModelScope
import com.studyround.app.data.repository.dashboard.DashboardRepository
import com.studyround.app.data.resource.Resource
import com.studyround.app.data.resource.windowedLoadDebounce
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

class DashboardHomeViewModel(
    private val dashboardRepository: DashboardRepository,
) : UdfViewModel<DashboardHomeViewState, DashboardHomeViewEvent>(),
    WithEffects<DashboardHomeViewEffect> {

    private val _viewState = MutableStateFlow(DashboardHomeViewState())
    override val viewState: StateFlow<DashboardHomeViewState>
        get() = _viewState.asStateFlow()

    private val _viewEffects = Channel<DashboardHomeViewEffect>(Channel.BUFFERED)
    override val viewEffects: Flow<DashboardHomeViewEffect>
        get() = _viewEffects.receiveAsFlow()

    init {
        viewModelScope.launch {
            fetchCategorisedCourses()
        }
    }

    override fun processEvent(event: DashboardHomeViewEvent) {
        when (event) {
            is CourseClicked -> {
                viewModelScope.launch {
                    _viewEffects.send(NavigateToCourse(event.course))
                }
            }

            is ViewCategoryClicked -> {
                viewModelScope.launch {
                    _viewEffects.send(NavigateToCoursesInCategory(event.category))
                }
            }

            is RetryLoadTriggered -> {
                viewModelScope.launch { fetchCategorisedCourses(event.isRefresh) }
            }
        }
    }

    private suspend fun fetchCategorisedCourses(isRefresh: Boolean = false) {
        dashboardRepository.fetchCategorisedCourses(isRefresh).windowedLoadDebounce(
            loadingWindow = if (isRefresh) 0 else 100L
        ).collect {
            when (it) {
                is Resource.Loading -> {
                    _viewState.update { state ->
                        state.copy(
                            loading = true,
                            refreshLoading = isRefresh,
                            error = false,
                        )
                    }
                }

                is Resource.Success -> {
                    _viewState.update { state ->
                        state.copy(
                            loading = false,
                            refreshLoading = false,
                            error = false,
                            categorisedCourses = it.data,
                        )
                    }
                }

                is Resource.Error -> {
                    _viewState.update { state ->
                        state.copy(
                            loading = false,
                            refreshLoading = false,
                            error = true,
                        )
                    }
                }
            }
        }
    }
}
