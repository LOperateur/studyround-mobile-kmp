package com.studyround.app.di

import com.studyround.app.repository.auth.AuthRepository
import com.studyround.app.repository.auth.AuthRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}
