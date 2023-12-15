package com.studyround.app.auth.session

import com.studyround.app.auth.email.EmailAuthProvider
import com.studyround.app.auth.model.AuthProviderType
import com.studyround.app.auth.model.EmailAuthProviderType
import com.studyround.app.auth.model.GoogleAuthProviderType
import com.studyround.app.platform.auth.GoogleAuthProvider
import com.studyround.app.storage.CredentialsManager
import com.studyround.app.storage.AppPreferences
import kotlinx.coroutines.flow.StateFlow

class SessionManagerImpl(
    private val googleAuthProvider: GoogleAuthProvider,
    private val emailAuthProvider: EmailAuthProvider,
    private val appPreferences: AppPreferences,
    private val credentialsManager: CredentialsManager,
) : SessionManager {

    override val isSignedIn: StateFlow<Boolean>
        get() = credentialsManager.hasValidCredentials

    override suspend fun signUp(type: AuthProviderType) {
        when (type) {
            is EmailAuthProviderType -> {
                emailAuthProvider.signup(
                    username = type.userIdentity,
                    password = type.password,
                    passToken = appPreferences.lastSavedPassToken,
                    onAuthResult = {},
                    onAuthError = {},
                )
            }

            is GoogleAuthProviderType -> {
                googleAuthProvider.login(
                    context = type.context,
                    onAuthResult = {},
                    onAuthError = {},
                )
            }
        }
    }

    override suspend fun login(type: AuthProviderType) {
        when (type) {
            is EmailAuthProviderType -> {
                emailAuthProvider.login(
                    userIdentity = type.userIdentity,
                    password = type.password,
                    onAuthResult = {},
                    onAuthError = {},
                )
            }

            is GoogleAuthProviderType -> {
                googleAuthProvider.login(
                    context = type.context,
                    onAuthResult = {},
                    onAuthError = {},
                )
            }
        }
    }

    override suspend fun logout() {
        googleAuthProvider.logout()
        credentialsManager.clearCredentials()
    }

    override fun reset(password: String) {
        emailAuthProvider.resetPassword(
            password = password,
            passToken = appPreferences.lastSavedPassToken,
            onAuthResult = {},
            onAuthError = {},
        )
    }

    override fun refreshToken() {

    }
}
