package com.studyround.app.ui.features.survey

import com.studyround.app.ui.composables.alert.AlertBannerType
import com.studyround.app.utils.AppString

sealed interface RegSurveyViewEffect

data class ShowAlert(val message: AppString, val type: AlertBannerType) : RegSurveyViewEffect
