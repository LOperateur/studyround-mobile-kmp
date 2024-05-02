package com.studyround.app.ui.features.survey

import com.studyround.app.ui.composables.dropdown.DropdownItem
import com.studyround.app.utils.AppString

sealed interface RegSurveyViewEvent

data object SubmitButtonClicked: RegSurveyViewEvent

data class OccupationDropdownItemSelected(val occupation: DropdownItem<String>): RegSurveyViewEvent

data class GradeDropdownItemSelected(val grade: DropdownItem<String>): RegSurveyViewEvent

data class JobTitleTextChanged(val title: String): RegSurveyViewEvent

data class AwarenessSourceChanged(val source: String): RegSurveyViewEvent
