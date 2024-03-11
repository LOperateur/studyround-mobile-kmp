package com.studyround.app.ui.features.survey

sealed interface RegSurveyViewEvent

data object SubmitButtonClicked: RegSurveyViewEvent

data class OccupationDropdownItemSelected(val occupation: String): RegSurveyViewEvent

data class GradeDropdownItemSelected(val grade: String): RegSurveyViewEvent

data class JobTitleTextChanged(val title: String): RegSurveyViewEvent

data class AwarenessSourceChanged(val source: String): RegSurveyViewEvent
