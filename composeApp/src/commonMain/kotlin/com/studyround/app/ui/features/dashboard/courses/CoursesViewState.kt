package com.studyround.app.ui.features.dashboard.courses

import com.studyround.app.domain.model.Course

data class CoursesViewState(
    val loading: Boolean = false,
    val courses: List<Course> = listOf(),
    val error: Boolean = false,
    val networkFetchComplete: Boolean = false, // Determines if the "Load more" button shows
    val hasFetchedCourses: Boolean = false,

    // Pagination
    val latestPage: Int = 0,
    val loadingMore: Boolean = false,
    val loadMoreError: Boolean = false,
) {
    val loadingWithoutData
        get() = loading && courses.isEmpty()

    val loadingWithData
        get() = loading && courses.isNotEmpty()

    val showEmptyState
        get() = hasFetchedCourses && courses.isEmpty() && !loading && !error
}
