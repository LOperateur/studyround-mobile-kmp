package com.studyround.app.ui.features.dashboard.home

import com.studyround.app.domain.model.Category
import com.studyround.app.domain.model.Course

sealed interface DashboardHomeViewEffect

data class NavigateToCourse(val course: Course) : DashboardHomeViewEffect

data class NavigateToCoursesInCategory(val category: Category) : DashboardHomeViewEffect
