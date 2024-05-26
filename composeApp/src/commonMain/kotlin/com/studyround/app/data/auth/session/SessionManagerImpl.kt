package com.studyround.app.data.auth.session

import com.studyround.app.data.auth.email.EmailAuthProvider
import com.studyround.app.data.auth.model.AuthCredentials
import com.studyround.app.data.auth.model.AuthType
import com.studyround.app.data.auth.model.EmailAuthType
import com.studyround.app.data.auth.model.GoogleAuthType
import com.studyround.app.data.mapper.dto_entity.toEntity
import com.studyround.app.data.model.remote.dto.AccessToken
import com.studyround.app.data.model.remote.dto.AuthUser
import com.studyround.app.data.model.remote.dto.User
import com.studyround.app.platform.auth.GoogleAuthProvider
import com.studyround.app.data.resource.Resource
import com.studyround.app.data.resource.wrappedResourceFlow
import com.studyround.app.data.service.auth.AuthService
import com.studyround.app.data.storage.preferences.AppPreferences
import com.studyround.app.data.storage.credentials.CredentialsManager
import com.studyround.app.data.storage.dao.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class SessionManagerImpl(
    private val emailAuthProvider: EmailAuthProvider,
    private val googleAuthProvider: GoogleAuthProvider,
    private val credentialsManager: CredentialsManager,
    private val authService: AuthService,
    private val appPreferences: AppPreferences,
    private val userDao: UserDao,
) : SessionManager {

    override val isSignedIn: StateFlow<Boolean>
        get() = credentialsManager.hasValidCredentials

    override fun signUp(type: AuthType): Flow<Resource<User>> {
        return when (type) {
            is EmailAuthType -> {
                emailAuthProvider.signup(
                    username = type.userIdentity,
                    password = type.password,
                    passToken = type.passToken,
                ).toUserResourceFlow(true)
            }

            is GoogleAuthType -> {
                wrappedResourceFlow {
                    val idToken = googleAuthProvider.login(type.context).token
                    authService.googleOauth(idToken)
                }.toUserResourceFlow(true)
            }
        }
    }

    override fun login(type: AuthType): Flow<Resource<User>> {
        return when (type) {
            is EmailAuthType -> {
                emailAuthProvider.login(
                    userIdentity = type.userIdentity,
                    password = type.password,
                ).toUserResourceFlow(false)
            }

            is GoogleAuthType -> {
                wrappedResourceFlow {
                    val idToken = googleAuthProvider.login(type.context).token
                    authService.googleOauth(idToken)
                }.toUserResourceFlow(false)
            }
        }
    }

    override suspend fun logout() {
        googleAuthProvider.logout()
        appPreferences.clear()
        credentialsManager.clearCredentials()
    }

    override fun reset(password: String, passToken: String): Flow<Resource<User>> {
        return emailAuthProvider.resetPassword(
            password = password,
            passToken = passToken,
        ).toUserResourceFlow(false)
    }

    override fun refreshToken(refreshToken: String): Flow<Resource<AccessToken>> {
        return wrappedResourceFlow {
            authService.refreshToken(refreshToken)
        }
    }

    private fun Flow<Resource<AuthUser>>.toUserResourceFlow(isSignup: Boolean = false): Flow<Resource<User>> {
        return this.map {
            when (it) {
                is Resource.Loading -> Resource.Loading(data = it.data?.user)
                is Resource.Error -> Resource.Error(data = it.data?.user, cause = it.cause)
                is Resource.Success -> {
                    // If logging in, skip the registration survey
                    if (!isSignup) appPreferences.setShouldDisplaySurveyScreen(false)

                    // Save the user data and credentials
                    it.data.user.toEntity().let { user ->
                        appPreferences.saveProfile(user)
                        userDao.insertUser(user)
                    }

                    saveCredentials(it.data)

                    Resource.Success(data = it.data.user, message = it.message)
                }
            }
        }
    }

    private fun saveCredentials(authUser: AuthUser) {
        credentialsManager.saveCredentials(
            AuthCredentials(
                authUser.accessToken,
                authUser.refreshToken,
            )
        )
    }
}
