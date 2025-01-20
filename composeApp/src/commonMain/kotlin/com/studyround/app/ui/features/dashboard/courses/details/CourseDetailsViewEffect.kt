package com.studyround.app.ui.features.dashboard.courses.details

import com.studyround.app.ui.composables.alert.AlertBannerType
import com.studyround.app.utils.AppString

sealed interface CourseDetailsViewEffect

data class ShowAlert(val message: AppString, val type: AlertBannerType) : CourseDetailsViewEffect
