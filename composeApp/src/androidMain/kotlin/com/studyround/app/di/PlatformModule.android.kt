package com.studyround.app.di

import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import com.studyround.app.platform.utils.AndroidBuildTargetInfo
import com.studyround.app.platform.utils.AndroidNetworkHelper
import com.studyround.app.platform.utils.BuildTargetInfo
import com.studyround.app.platform.utils.Credentials
import com.studyround.app.platform.utils.NetworkHelper
import com.studyround.app.storage.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule = module {
    single<BuildTargetInfo> { AndroidBuildTargetInfo() }

    single<NetworkHelper> { AndroidNetworkHelper(androidContext()) }

    single<Settings>(named(NAMED_SETTINGS)) { SharedPreferencesSettings(Preferences.createSettingsPrefs(androidContext()),true) }

    single<Credentials>(named(NAMED_CREDENTIALS)) { SharedPreferencesSettings(Preferences.createCredentialsPrefs(androidContext()), true) }
}
