package com.studyround.app.di

import com.studyround.app.platform.Platform
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    platform: Platform,
    appDeclaration: KoinAppDeclaration = {},
) {
    startKoin {
        appDeclaration()
        modules(
            listOf(
                commonModule,
                platformModule(platform),
            )
        )
    }
}
