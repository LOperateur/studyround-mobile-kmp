package com.studyround.app.di

import com.studyround.app.platform.IosApplicationComponent
import com.studyround.app.platform.utils.NetworkListener
import org.koin.dsl.module

actual val platformModule = module {
    single { get<IosApplicationComponent>().buildTargetInfo }
    single { get<IosApplicationComponent>().networkHelper }
    single { NetworkListener(get()) }
}
