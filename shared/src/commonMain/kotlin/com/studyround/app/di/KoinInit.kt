package com.studyround.app.di

import com.studyround.app.platform.SharedPlatform
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(
    platform: SharedPlatform,
    appDeclaration: KoinAppDeclaration = {},
) {
    startKoin {
        appDeclaration()
        modules(
            listOf(
                module { single { platform } },
                commonModule,
                platformModule,
            )
        )
    }
}
