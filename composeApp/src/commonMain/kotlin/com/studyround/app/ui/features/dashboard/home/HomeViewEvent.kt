package com.studyround.app.ui.features.dashboard.home

import com.studyround.app.domain.model.Category
import com.studyround.app.domain.model.Course

sealed interface HomeViewEvent

data class CourseClicked(val course: Course): HomeViewEvent

data class ViewCategoryClicked(val category: Category): HomeViewEvent

data class RetryLoadTriggered(val isRefresh: Boolean): HomeViewEvent
