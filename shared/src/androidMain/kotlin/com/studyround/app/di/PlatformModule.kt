package com.studyround.app.di

import com.studyround.app.platform.Platform
import org.koin.dsl.module

actual fun platformModule(platform: Platform) = module {
    single { platform }
    single { platform.sampleComponent }
}
