package com.studyround.app.ui.features.dashboard.courses

sealed interface CoursesViewEvent

data object RetryLoadTriggered: CoursesViewEvent

data object LoadMoreClicked: CoursesViewEvent
