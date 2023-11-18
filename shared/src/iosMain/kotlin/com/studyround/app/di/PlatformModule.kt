package com.studyround.app.di

import com.studyround.app.platform.PlatformComponents
import org.koin.dsl.module

actual fun platformModule(platformComponent: PlatformComponents) = module {
    single { platformComponent }
    single { platformComponent.sampleComponent }
}
