package com.studyround.app.di

import com.studyround.app.ui.composables.alert.AlertBannerViewModel
import com.studyround.app.ui.composables.common.appbar.AppBarViewModel
import com.studyround.app.ui.features.auth.login.LoginViewModel
import com.studyround.app.ui.features.auth.otp.OtpViewModel
import com.studyround.app.ui.features.auth.register.RegisterViewModel
import com.studyround.app.ui.features.dashboard.courses.CoursesViewModel
import com.studyround.app.ui.features.dashboard.home.DashboardHomeViewModel
import com.studyround.app.ui.features.survey.RegSurveyViewModel
import com.studyround.app.ui.main.RootViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { RootViewModel(get(), get(), get()) }
    factory { AlertBannerViewModel() }
    factory { LoginViewModel(get(), get()) }
    factory { OtpViewModel(get()) }
    factory { RegisterViewModel(get()) }
    factory { RegSurveyViewModel(get()) }
    factory { AppBarViewModel(get()) }
    factory { DashboardHomeViewModel(get()) }
    factory { CoursesViewModel(get()) }
}
