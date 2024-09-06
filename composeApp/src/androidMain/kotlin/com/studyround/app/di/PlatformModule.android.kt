package com.studyround.app.di

import androidx.credentials.CredentialManager
import androidx.room.RoomDatabase
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import com.studyround.app.data.storage.Database
import com.studyround.app.platform.auth.AndroidGoogleAuthProvider
import com.studyround.app.platform.auth.GoogleAuthProvider
import com.studyround.app.platform.utils.AndroidImageLoader
import com.studyround.app.platform.utils.AndroidNetworkHelper
import com.studyround.app.platform.utils.AndroidPlatform
import com.studyround.app.platform.utils.NetworkHelper
import com.studyround.app.platform.utils.Platform
import com.studyround.app.platform.utils.StudyRoundImageLoader
import com.studyround.app.data.storage.Preferences
import com.studyround.app.data.storage.StudyRoundDatabase
import com.studyround.app.platform.utils.AndroidPlatformFileProvider
import com.studyround.app.platform.utils.PlatformFileProvider
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule = module {
    single<Platform> { AndroidPlatform() }

    single<NetworkHelper> { AndroidNetworkHelper(androidContext()) }

    single<ObservableSettings>(named(NAMED_SETTINGS)) {
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

    single<PlatformFileProvider> { AndroidPlatformFileProvider(androidContext()) }

    single<GoogleAuthProvider> { AndroidGoogleAuthProvider(get()) }

    single<HttpClientEngine> { OkHttp.create { preconfigured = OkHttpClient().newBuilder().build() } }

    single<StudyRoundImageLoader> { AndroidImageLoader(androidContext(), get()) }

    single<RoomDatabase.Builder<StudyRoundDatabase>> { Database.createRoomDatabase(androidContext(), get()) }
}
