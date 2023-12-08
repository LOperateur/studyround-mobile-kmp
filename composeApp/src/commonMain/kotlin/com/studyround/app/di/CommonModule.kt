package com.studyround.app.di

import com.studyround.app.platform.utils.NetworkListener
import com.studyround.app.storage.SettingsWrapper
import com.studyround.app.storage.SettingsWrapperImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val commonModule = module {
    single { NetworkListener(get()) }
    single<SettingsWrapper> { SettingsWrapperImpl(get(qualifier = named(NAMED_SETTINGS))) }
}
