package com.studyround.app.ui.features.dashboard.courses.details

import com.studyround.app.domain.model.Course

data class CourseDetailsViewState(
    val loading: Boolean = false,
    val course: Course? = null,
    val error: Boolean = false,
)
