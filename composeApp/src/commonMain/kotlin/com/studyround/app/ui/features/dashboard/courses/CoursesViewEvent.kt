package com.studyround.app.ui.features.dashboard.courses

sealed interface CoursesViewEvent

data object RetryLoadClicked: CoursesViewEvent
