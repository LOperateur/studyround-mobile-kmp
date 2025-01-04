package com.studyround.app.ui.features.dashboard.home

import com.studyround.app.domain.model.Category
import com.studyround.app.domain.model.Course

sealed interface HomeViewEffect

data class NavigateToCourse(val course: Course) : HomeViewEffect

data class NavigateToCoursesInCategory(val category: Category) : HomeViewEffect
