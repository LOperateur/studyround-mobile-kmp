package com.studyround.app.di

import com.studyround.app.data.repository.auth.AuthRepository
import com.studyround.app.data.repository.auth.AuthRepositoryImpl
import com.studyround.app.data.repository.dashboard.DashboardRepository
import com.studyround.app.data.repository.dashboard.DashboardRepositoryImpl
import com.studyround.app.data.repository.survey.RegSurveyRepository
import com.studyround.app.data.repository.survey.RegSurveyRepositoryImpl
import com.studyround.app.data.storage.StudyRoundDatabase
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<RegSurveyRepository> {
        RegSurveyRepositoryImpl(
            get(),
            get(),
            get<StudyRoundDatabase>().userDao(),
        )
    }
    single<DashboardRepository> { DashboardRepositoryImpl(get()) }
}
