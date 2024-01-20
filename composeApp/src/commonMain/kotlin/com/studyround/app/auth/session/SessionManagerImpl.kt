package com.studyround.app.auth.session

import com.studyround.app.auth.email.EmailAuthProvider
import com.studyround.app.auth.model.AuthProviderType
import com.studyround.app.auth.model.EmailAuthProviderType
import com.studyround.app.auth.model.GoogleAuthProviderType
import com.studyround.app.platform.auth.GoogleAuthProvider
import com.studyround.app.service.data.resource.Resource
import com.studyround.app.storage.CredentialsManager
import com.studyround.app.storage.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow

class SessionManagerImpl(
    private val googleAuthProvider: GoogleAuthProvider,
    private val emailAuthProvider: EmailAuthProvider,
    private val appPreferences: AppPreferences,
    private val credentialsManager: CredentialsManager,
) : SessionManager {

    override val isSignedIn: StateFlow<Boolean>
        get() = credentialsManager.hasValidCredentials

    override fun signUp(type: AuthProviderType): Flow<Resource<Unit>> {
        return when (type) {
            is EmailAuthProviderType -> {
                emailAuthProvider.signup(
                    username = type.userIdentity,
                    password = type.password,
                    passToken = appPreferences.lastSavedPassToken,
                )
            }

            is GoogleAuthProviderType -> {
                channelFlow {
                    send(Resource.Loading())
                    googleAuthProvider.login(
                        context = type.context,
                        onAuthResult = { trySend(Resource.Success(Unit)) },
                        onAuthError = { trySend(Resource.Error(cause = it)) },
                    )
                }
            }
        }
    }

    override fun login(type: AuthProviderType): Flow<Resource<Unit>> {
        return when (type) {
            is EmailAuthProviderType -> {
                emailAuthProvider.login(
                    userIdentity = type.userIdentity,
                    password = type.password,
                )
            }

            is GoogleAuthProviderType -> {
                channelFlow {
                    send(Resource.Loading())
                    googleAuthProvider.login(
                        context = type.context,
                        onAuthResult = { trySend(Resource.Success(Unit)) },
                        onAuthError = { trySend(Resource.Error(cause = it)) },
                    )
                }
            }
        }
    }

    override suspend fun logout() {
        googleAuthProvider.logout()
        credentialsManager.clearCredentials()
    }

    override fun reset(password: String): Flow<Resource<Unit>> {
        return emailAuthProvider.resetPassword(
            password = password,
            passToken = appPreferences.lastSavedPassToken,
        )
    }

    override fun refreshToken(refreshToken: String): Flow<Resource<Unit>> {
        return emailAuthProvider.refreshToken(refreshToken)
    }
}
