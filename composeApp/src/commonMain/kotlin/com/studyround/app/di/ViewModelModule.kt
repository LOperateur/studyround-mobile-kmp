package com.studyround.app.di

import com.studyround.app.ui.composables.alert.AlertBannerViewModel
import com.studyround.app.ui.composables.common.appbar.AppBarViewModel
import com.studyround.app.ui.features.auth.login.LoginViewModel
import com.studyround.app.ui.features.auth.otp.OtpViewModel
import com.studyround.app.ui.features.auth.register.RegisterViewModel
import com.studyround.app.ui.features.dashboard.courses.CoursesViewModel
import com.studyround.app.ui.features.dashboard.courses.details.CourseDetailsViewModel
import com.studyround.app.ui.features.dashboard.home.HomeViewModel
import com.studyround.app.ui.features.survey.RegSurveyViewModel
import com.studyround.app.ui.main.RootViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::RootViewModel)
    viewModelOf(::AlertBannerViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::OtpViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegSurveyViewModel)
    viewModelOf(::AppBarViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CoursesViewModel)
    viewModelOf(::CourseDetailsViewModel)
}
