package com.studyround.app.ui.features.dashboard.home

import com.studyround.app.domain.model.Category

data class DashboardHomeViewState(
    val loading: Boolean = false,
    val categorisedCourses: List<Category> = listOf(),
)
