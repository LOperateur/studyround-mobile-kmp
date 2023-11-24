package com.studyround.app.di

import com.studyround.app.platform.AndroidApplicationComponent
import com.studyround.app.platform.utils.NetworkListener
import org.koin.dsl.module

actual val platformModule = module {
    single { get<AndroidApplicationComponent>().buildTargetInfo }
    single { get<AndroidApplicationComponent>().networkHelper }
    single { NetworkListener(get()) }
}
