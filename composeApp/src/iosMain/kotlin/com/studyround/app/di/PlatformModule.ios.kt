package com.studyround.app.di

import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings
import com.studyround.app.data.storage.Database
import com.studyround.app.data.storage.StudyRoundDatabase
import com.studyround.app.platform.IosApplicationComponent
import com.studyround.app.platform.auth.GoogleAuthProvider
import com.studyround.app.platform.utils.IosImageLoader
import com.studyround.app.platform.utils.IosPlatform
import com.studyround.app.platform.utils.IosPlatformFileProvider
import com.studyround.app.platform.utils.NetworkHelper
import com.studyround.app.platform.utils.Platform
import com.studyround.app.platform.utils.PlatformFileProvider
import com.studyround.app.platform.utils.StudyRoundImageLoader
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

@OptIn(ExperimentalSettingsImplementation::class)
actual val platformModule = module {
    single<Platform> { IosPlatform() }

    single<NetworkHelper> { get<IosApplicationComponent>().networkHelper }

    single<ObservableSettings>(named(NAMED_SETTINGS)) { NSUserDefaultsSettings(NSUserDefaults(suiteName = "com.operator.studyround.shared")) }

    single<Credentials>(named(NAMED_CREDENTIALS)) { KeychainSettings("com.operator.studyround.secrets") }

    single<GoogleAuthProvider> { get<IosApplicationComponent>().googleAuthProvider }

    single<PlatformFileProvider> { IosPlatformFileProvider() }

    single<HttpClientEngine> { Darwin.create() }

    single<StudyRoundImageLoader> { IosImageLoader(get()) }

    single<StudyRoundDatabase> { Database.createRoomDatabase(get()) }
}
