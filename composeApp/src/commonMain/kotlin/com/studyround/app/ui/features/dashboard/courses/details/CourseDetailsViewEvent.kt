package com.studyround.app.ui.features.dashboard.courses.details

sealed interface CourseDetailsViewEvent

data object CloseButtonClicked : CourseDetailsViewEvent

data object CoursePrimaryCtaClicked : CourseDetailsViewEvent
