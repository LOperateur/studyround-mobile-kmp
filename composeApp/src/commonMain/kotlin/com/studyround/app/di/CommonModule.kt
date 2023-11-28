package com.studyround.app.di

import com.studyround.app.platform.utils.NetworkListener
import org.koin.dsl.module

val commonModule = module {
    single { NetworkListener(get()) }
}
