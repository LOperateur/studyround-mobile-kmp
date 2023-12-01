package com.studyround.app.di

import androidx.credentials.CredentialManager
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import com.studyround.app.platform.auth.AndroidGoogleAuthProvider
import com.studyround.app.platform.auth.GoogleAuthProvider
import com.studyround.app.platform.utils.AndroidNetworkHelper
import com.studyround.app.platform.utils.AndroidPlatform
import com.studyround.app.platform.utils.Credentials
import com.studyround.app.platform.utils.NetworkHelper
import com.studyround.app.platform.utils.Platform
import com.studyround.app.storage.Preferences
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule = module {
    single<Platform> { AndroidPlatform() }

    single<NetworkHelper> { AndroidNetworkHelper(androidContext()) }

    single<Settings>(named(NAMED_SETTINGS)) {
        SharedPreferencesSettings(
            Preferences.createSettingsPrefs(
                androidContext()
            ), true
        )
    }

    single<Credentials>(named(NAMED_CREDENTIALS)) {
        SharedPreferencesSettings(
            Preferences.createCredentialsPrefs(
                androidContext()
            ), true
        )
    }

    single<CredentialManager> { CredentialManager.create(androidContext()) }

    single<GoogleAuthProvider> {
        AndroidGoogleAuthProvider(
            get(),
            androidApplication(),
        )
    }
}
