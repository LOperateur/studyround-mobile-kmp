package com.studyround.app.ui.features.survey

import com.studyround.app.utils.AppString
import com.studyround.app.utils.AppStrings

data class RegSurveyViewState(
    val occupationSelection: String? = null,
    val gradeSelection: String? = null,
    val jobTitle: String? = null,
    val awarenessSource: String? = null,
    val submissionLoading: Boolean = false,
) {
    val occupations = listOf(
        AppStrings.STUDENT_OCCUPATION,
        AppStrings.PROFESSIONAL_OCCUPATION,
    ).map { AppString(it) }

    val grades = listOf(
        AppStrings.PRIMARY_GRADE,
        AppStrings.SECONDARY_GRADE,
        AppStrings.TERTIARY_GRADE,
        AppStrings.POST_GRADUATE_GRADE,
    ).map { AppString(it) }

    val awarenessSources = listOf(
        AppStrings.GOOGLE,
        AppStrings.FACEBOOK,
        AppStrings.INSTAGRAM,
        AppStrings.EMAIL,
        AppStrings.FRIEND_RECOMMENDATION,
        AppStrings.OTHER,
    ).map { AppString(it) }
}

data class RegSurveyTextFieldState(val professionText: String = "")
