package com.studyround.app.auth.email

import com.studyround.app.data.remote.dto.AuthUser
import com.studyround.app.service.data.resource.Resource
import com.studyround.app.service.data.resource.wrappedResourceFlow
import com.studyround.app.service.auth.AuthService
import kotlinx.coroutines.flow.Flow

internal class EmailAuthProviderImpl(private val authService: AuthService) : EmailAuthProvider {
    override fun signup(
        username: String,
        password: String,
        passToken: String,
    ): Flow<Resource<AuthUser>> {
        return wrappedResourceFlow {
            authService.signup(username, password, passToken)
        }
    }

    override fun login(
        userIdentity: String,
        password: String,
    ): Flow<Resource<AuthUser>> {
        return wrappedResourceFlow {
            authService.login(userIdentity, password)
        }
    }

    override fun resetPassword(
        password: String,
        passToken: String,
    ): Flow<Resource<AuthUser>> {
        return wrappedResourceFlow {
            authService.resetPassword(password, passToken)
        }
    }
}
