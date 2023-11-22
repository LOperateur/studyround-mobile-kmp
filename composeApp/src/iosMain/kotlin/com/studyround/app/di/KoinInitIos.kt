package com.studyround.app.di

import com.studyround.app.platform.IosApplicationComponent
import org.koin.dsl.module

fun initKoinIos(appComponent: IosApplicationComponent) {
    initKoin(
        listOf(module { single { appComponent } })
    )
}
