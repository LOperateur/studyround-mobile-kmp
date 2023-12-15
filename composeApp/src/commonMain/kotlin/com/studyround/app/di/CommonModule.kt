package com.studyround.app.di

import com.studyround.app.auth.email.EmailAuthProvider
import com.studyround.app.auth.email.EmailAuthProviderImpl
import com.studyround.app.auth.session.SessionManager
import com.studyround.app.auth.session.SessionManagerImpl
import com.studyround.app.data.utils.NetworkListener
import com.studyround.app.storage.AppPreferences
import com.studyround.app.storage.AppPreferencesImpl
import com.studyround.app.storage.CredentialsManager
import com.studyround.app.storage.SecureCredentialsManager
import org.koin.core.qualifier.named
import org.koin.dsl.module

val commonModule = module {
    single { NetworkListener(get()) }
    single<AppPreferences> { AppPreferencesImpl(get(qualifier = named(NAMED_SETTINGS))) }
    single<EmailAuthProvider> { EmailAuthProviderImpl() }
    single<CredentialsManager> { SecureCredentialsManager(get(qualifier = named(NAMED_CREDENTIALS))) }
    single<SessionManager> { SessionManagerImpl(get(), get(), get(), get()) }
}
