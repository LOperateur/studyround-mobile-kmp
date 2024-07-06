package com.studyround.app.ui.features.dashboard.courses

import com.studyround.app.ui.composables.alert.AlertBannerType
import com.studyround.app.utils.AppString

sealed interface CoursesViewEffect

data class ShowAlert(val message: AppString, val type: AlertBannerType) : CoursesViewEffect
