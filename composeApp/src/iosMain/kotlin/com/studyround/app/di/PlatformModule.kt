package com.studyround.app.di

import com.russhwolf.settings.Settings
import com.studyround.app.platform.Credentials
import com.studyround.app.platform.IosApplicationComponent
import org.koin.dsl.module

actual val platformModule = module {
    single { get<IosApplicationComponent>().buildTargetInfo }
    single { get<IosApplicationComponent>().networkHelper }
    single<Settings> { get<IosApplicationComponent>().settings }
    single<Credentials> { get<IosApplicationComponent>().credentials }
}
