package com.studyround.app.di

import com.studyround.app.data.auth.email.EmailAuthProvider
import com.studyround.app.data.auth.email.EmailAuthProviderImpl
import com.studyround.app.data.auth.session.SessionManager
import com.studyround.app.data.auth.session.SessionManagerImpl
import com.studyround.app.data.storage.StudyRoundDatabase
import com.studyround.app.utils.NetworkListener
import com.studyround.app.data.storage.preferences.AppPreferences
import com.studyround.app.data.storage.preferences.AppPreferencesImpl
import com.studyround.app.data.storage.credentials.CredentialsManager
import com.studyround.app.data.storage.credentials.SecureCredentialsManager
import org.koin.core.qualifier.named
import org.koin.dsl.module

val commonModule = module {
    single<NetworkListener> { NetworkListener(get()) }
    single<AppPreferences> { AppPreferencesImpl(get(qualifier = named(NAMED_SETTINGS))) }
    single<CredentialsManager> {
        SecureCredentialsManager(
            get(qualifier = named(NAMED_CREDENTIALS)),
            get(qualifier = named(NAMED_SETTINGS)),
        )
    }
    single<EmailAuthProvider> { EmailAuthProviderImpl(get()) }
    single<SessionManager> {
        SessionManagerImpl(
            get(),
            get(),
            get(),
            get(),
            get(),
            get<StudyRoundDatabase>().userDao(),
        )
    }
}
