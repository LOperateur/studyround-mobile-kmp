package com.studyround.app.ui.features.dashboard.home

import com.studyround.app.data.model.remote.dto.Category
import com.studyround.app.data.model.remote.dto.Course

sealed interface DashboardHomeViewEvent

data class CourseClicked(val course: Course): DashboardHomeViewEvent

data class ViewCategoryClicked(val category: Category): DashboardHomeViewEvent
