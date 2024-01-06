package com.studyround.app.di

import com.studyround.app.ui.features.auth.login.LoginViewModel
import com.studyround.app.ui.main.RootViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { RootViewModel(get(), get(), get()) }
    factory { LoginViewModel() }
}
