package com.studyround.app.ui.features.dashboard.home

import com.studyround.app.data.model.remote.dto.Category
import com.studyround.app.data.model.remote.dto.Course

sealed interface DashboardHomeViewEffect

data class NavigateToCourse(val course: Course) : DashboardHomeViewEffect

data class NavigateToCoursesInCategory(val category: Category) : DashboardHomeViewEffect
