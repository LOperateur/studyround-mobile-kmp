package com.studyround.app.di

import com.studyround.app.platform.IosPlatform
import com.studyround.app.platform.SharedPlatform
import com.studyround.app.platform.utils.NetworkListener
import org.koin.dsl.module

actual val platformModule = module {
    single { get<SharedPlatform>() as IosPlatform }
    single { get<IosPlatform>().sampleComponent }
    single { get<IosPlatform>().networkHelper }
    single { NetworkListener(get()) }
}
