package com.studyround.app.ui.features.dashboard.courses

import cafe.adriel.voyager.core.model.screenModelScope
import com.studyround.app.data.repository.dashboard.DashboardRepository
import com.studyround.app.data.resource.Resource
import com.studyround.app.data.resource.windowedLoadDebounce
import com.studyround.app.ui.viewmodel.UdfViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoursesViewModel(
    private val dashboardRepository: DashboardRepository,
) : UdfViewModel<CoursesViewState, CoursesViewEvent>() {

    private val _viewState = MutableStateFlow(CoursesViewState())
    override val viewState: StateFlow<CoursesViewState>
        get() = _viewState.asStateFlow()

    override fun processEvent(event: CoursesViewEvent) {
        when (event) {
            LoadMoreClicked -> {
                if (viewState.value.canLoadMore)
                    fetchCourses(viewState.value.latestPage + 1)
            }

            RetryLoadTriggered -> {
                fetchCourses(viewState.value.latestPage)
            }

            RefreshTriggered -> {
                fetchCourses(1, true)
            }
        }
    }

    private var fetchJob: Job? = null

    init {
        fetchCourses(1)
    }

    private fun fetchCourses(page: Int, isRefresh: Boolean = false) {
        var fetchCount = 0
        val perPageLimit = 12

        if (fetchJob != null && fetchJob?.isActive == true) return

        fetchJob = screenModelScope.launch {
            dashboardRepository.fetchCourses(page, perPageLimit, isRefresh)
                .windowedLoadDebounce(
                    loadingWindow = if (isRefresh) 0 else 200L
                ).collect {
                    when (it) {
                        is Resource.Loading -> {
                            if (page == 1) {
                                _viewState.update { state ->
                                    state.copy(
                                        loading = true,
                                        refreshLoading = isRefresh,
                                        error = false
                                    )
                                }
                            } else {
                                _viewState.update { state ->
                                    state.copy(loadingMore = true, loadMoreError = false)
                                }
                            }
                        }

                        is Resource.Success -> {
                            ++fetchCount
                            val total = it.pageData?.total ?: 0
                            val pageCount = ((total - 1).coerceAtLeast(0) / perPageLimit) + 1

                            if (page == 1) {
                                _viewState.update { state ->
                                    state.copy(
                                        loading = false,
                                        refreshLoading = false,
                                        error = false,
                                        courses = it.data,
                                        latestPage = page,
                                        pageCount = pageCount,
                                        hasFetchedCourses = true,
                                        networkFetchComplete = fetchCount > 1 || isRefresh,
                                    )
                                }
                            } else {
                                val courses = viewState.value.courses
                                _viewState.update { state ->
                                    state.copy(
                                        loadingMore = false,
                                        loadMoreError = false,
                                        courses = courses + it.data,
                                        latestPage = page,
                                        pageCount = pageCount,
                                    )
                                }
                            }
                        }

                        is Resource.Error -> {
                            if (page == 1) {
                                if (viewState.value.courses.isEmpty()) {
                                    _viewState.update { state ->
                                        state.copy(
                                            loading = false,
                                            refreshLoading = false,
                                            error = true
                                        )
                                    }
                                } else {
                                    _viewState.update { state ->
                                        state.copy(
                                            loading = false,
                                            refreshLoading = false,
                                        )
                                    }
                                    // Todo: Send alert
                                }
                            } else {
                                _viewState.update { state ->
                                    state.copy(loadingMore = false, loadMoreError = true)
                                }
                            }
                        }
                    }
                }
        }
    }
}
