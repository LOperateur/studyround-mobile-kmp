package com.studyround.app.di

import com.studyround.app.platform.AndroidApplicationComponent
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoinAndroid(
    appComponent: AndroidApplicationComponent,
    appDeclaration: KoinAppDeclaration = {},
) {
    initKoin(
        listOf(module { single { appComponent } }),
        appDeclaration,
    )
}
