package com.studyround.app.di

import com.studyround.app.platform.IosPlatform
import com.studyround.app.platform.Platform
import com.studyround.app.platform.components.SampleComponent
import org.koin.dsl.module

actual fun platformModule(platform: Platform) = module {
    single { platform }
    single<SampleComponent> { get<IosPlatform>().sampleComponent }
}
