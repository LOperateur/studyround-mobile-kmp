package com.studyround.app.di

import com.studyround.app.repository.login.LoginRepository
import com.studyround.app.repository.login.LoginRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
}
