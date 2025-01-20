package com.studyround.app.ui.features.dashboard.courses

import com.studyround.app.domain.model.Course

data class CoursesViewState(
    val loading: Boolean = false,
    val refreshLoading: Boolean = false,
    val courses: List<Course> = listOf(),
    val error: Boolean = false,
    val hasFetchedCourses: Boolean = false,
    private val networkFetchComplete: Boolean = false, // Determines if the "Load more" button shows

    val selectedCategoryId: Long? = null,

    // Pagination
    val latestPage: Int = 1,
    val pageCount: Int = 1,
    val loadingMore: Boolean = false,
    val loadMoreError: Boolean = false,
) {
    val loadingWithoutData
        get() = loading && courses.isEmpty()

    val loadingWithData
        get() = loading && courses.isNotEmpty()

    val showEmptyState
        get() = hasFetchedCourses && courses.isEmpty() && !loading && !error

    val canLoadMore
        get() = networkFetchComplete && latestPage < pageCount
}
