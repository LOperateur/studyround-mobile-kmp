package com.studyround.app.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    additionalModules: List<Module> = listOf(),
    appDeclaration: KoinAppDeclaration = {},
) {
    startKoin {
        appDeclaration()
        modules(
            additionalModules +
            listOf(
                platformModule,
                commonModule,
                networkModule,
                viewModelModule,
                repositoryModule,
            )
        )
    }
}
