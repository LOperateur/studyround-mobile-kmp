package com.studyround.app.di

import com.studyround.app.platform.AndroidPlatform
import com.studyround.app.platform.SharedPlatform
import com.studyround.app.platform.utils.NetworkListener
import org.koin.dsl.module

actual val platformModule = module {
    single { get<SharedPlatform>() as AndroidPlatform}
    single { get<AndroidPlatform>().sampleComponent }
    single { get<AndroidPlatform>().networkHelper }
    single { NetworkListener(get()) }
}
