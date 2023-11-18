package com.studyround.app.di

import com.studyround.app.platform.PlatformComponents
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    platformComponents: PlatformComponents,
    appDeclaration: KoinAppDeclaration = {},
) {
    startKoin {
        appDeclaration()
        modules(
            listOf(
                commonModule,
                platformModule(platformComponents),
            )
        )
    }
}
