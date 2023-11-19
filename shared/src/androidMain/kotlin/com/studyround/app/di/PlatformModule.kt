package com.studyround.app.di

import com.studyround.app.platform.AndroidPlatform
import com.studyround.app.platform.components.SampleComponent
import org.koin.dsl.module

actual val platformModule = module {
    single<SampleComponent> { get<AndroidPlatform>().sampleComponent }
}
