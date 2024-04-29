package com.studyround.app.di

import com.studyround.app.repository.auth.AuthRepository
import com.studyround.app.repository.auth.AuthRepositoryImpl
import com.studyround.app.repository.survey.RegSurveyRepository
import com.studyround.app.repository.survey.RegSurveyRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<RegSurveyRepository> { RegSurveyRepositoryImpl(get()) }
}
