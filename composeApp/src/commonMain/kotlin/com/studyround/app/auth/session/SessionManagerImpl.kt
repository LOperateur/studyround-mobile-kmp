package com.studyround.app.auth.session

import com.studyround.app.auth.email.EmailAuthProvider
import com.studyround.app.auth.model.AuthCredentials
import com.studyround.app.auth.model.AuthType
import com.studyround.app.auth.model.EmailAuthType
import com.studyround.app.auth.model.GoogleAuthType
import com.studyround.app.data.remote.dto.AccessToken
import com.studyround.app.data.remote.dto.AuthUser
import com.studyround.app.data.remote.dto.User
import com.studyround.app.platform.auth.GoogleAuthProvider
import com.studyround.app.service.data.resource.Resource
import com.studyround.app.service.data.resource.wrappedResourceFlow
import com.studyround.app.service.login.LoginService
import com.studyround.app.storage.AppPreferences
import com.studyround.app.storage.CredentialsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SessionManagerImpl(
    private val emailAuthProvider: EmailAuthProvider,
    private val googleAuthProvider: GoogleAuthProvider,
//    private val appPreferences: AppPreferences,
    private val credentialsManager: CredentialsManager,
    private val loginService: LoginService,
) : SessionManager {

    override val isSignedIn: StateFlow<Boolean>
        get() = credentialsManager.hasValidCredentials

    override fun signUp(type: AuthType): Flow<Resource<User>> {
        return when (type) {
            is EmailAuthType -> {
                val passToken = type.passToken ?: run {
                    return flowOf(Resource.Error(cause = Exception("No Auth token provided, please try signing up again")))
                }

                emailAuthProvider.signup(
                    username = type.userIdentity,
                    password = type.password,
                    passToken = passToken,
                ).toUserResourceFlow(true)
            }

            is GoogleAuthType -> {
                wrappedResourceFlow {
                    val idToken = googleAuthProvider.login(type.context).token
                    loginService.googleOauth(idToken)
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
                    loginService.googleOauth(idToken)
                }.toUserResourceFlow(false)
            }
        }
    }

    override suspend fun logout() {
        googleAuthProvider.logout()
        credentialsManager.clearCredentials()
    }

    override fun reset(password: String, passToken: String): Flow<Resource<User>> {
//        val passToken = appPreferences.lastSavedPassToken ?: run {
//            return flowOf(Resource.Error(cause = Exception("No Auth token provided, please try signing up again")))
//        }

        return emailAuthProvider.resetPassword(
            password = password,
            passToken = passToken,
        ).toUserResourceFlow(false)
    }

    override fun refreshToken(refreshToken: String): Flow<Resource<AccessToken>> {
        return wrappedResourceFlow {
            loginService.refreshToken(refreshToken)
        }
    }

    private fun Flow<Resource<AuthUser>>.toUserResourceFlow(isSignup: Boolean = false): Flow<Resource<User>> {
        return this.map {
            when (it) {
                is Resource.Loading -> Resource.Loading(data = it.data?.user)
                is Resource.Error -> Resource.Error(data = it.data?.user, cause = it.cause)
                is Resource.Success -> {
                    if (!isSignup) { /* Todo: Indicate marketing screen to not be shown */ }
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
