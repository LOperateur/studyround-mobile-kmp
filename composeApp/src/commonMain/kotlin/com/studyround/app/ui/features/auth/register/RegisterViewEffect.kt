package com.studyround.app.ui.features.auth.register

import com.studyround.app.ui.composables.alert.AlertBannerType
import com.studyround.app.utils.AppString

sealed interface RegisterViewEffect

data class ShowAlert(val message: AppString, val type: AlertBannerType) : RegisterViewEffect
