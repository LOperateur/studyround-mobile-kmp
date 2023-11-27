package com.studyround.app.di

import com.russhwolf.settings.Settings
import com.studyround.app.platform.AndroidApplicationComponent
import com.studyround.app.platform.Credentials
import org.koin.dsl.module

actual val platformModule = module {
    single { get<AndroidApplicationComponent>().buildTargetInfo }
    single { get<AndroidApplicationComponent>().networkHelper }
    single<Settings> { get<AndroidApplicationComponent>().settings }
    single<Credentials> { get<AndroidApplicationComponent>().credentials }
}
