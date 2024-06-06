package com.studyround.app.ui.features.dashboard.home

import com.studyround.app.domain.model.Category
import com.studyround.app.domain.model.Course

sealed interface DashboardHomeViewEvent

data class CourseClicked(val course: Course): DashboardHomeViewEvent

data class ViewCategoryClicked(val category: Category): DashboardHomeViewEvent
