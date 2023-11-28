package com.studyround.app.di

import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import com.studyround.app.platform.IosApplicationComponent
import com.studyround.app.platform.utils.BuildTargetInfo
import com.studyround.app.platform.utils.Credentials
import com.studyround.app.platform.utils.IosBuildTargetInfo
import com.studyround.app.platform.utils.NetworkHelper
import org.koin.dsl.module

@OptIn(ExperimentalSettingsImplementation::class)
actual val platformModule = module {
    single<BuildTargetInfo> { IosBuildTargetInfo() }

    single<NetworkHelper> { get<IosApplicationComponent>().networkHelper }

    single<Settings> { NSUserDefaultsSettings(get<IosApplicationComponent>().userDefaults) }

    single<Credentials> { KeychainSettings("com.operator.studyround.secrets") }
}
