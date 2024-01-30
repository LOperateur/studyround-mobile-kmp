package com.studyround.app.auth.email

import com.studyround.app.data.remote.dto.AuthUser
import com.studyround.app.service.data.resource.Resource
import com.studyround.app.service.data.resource.wrappedResourceFlow
import com.studyround.app.service.login.LoginService
import kotlinx.coroutines.flow.Flow

internal class EmailAuthProviderImpl(private val loginService: LoginService) : EmailAuthProvider {
    override fun signup(
        username: String,
        password: String,
        passToken: String,
    ): Flow<Resource<AuthUser>> {
        return wrappedResourceFlow {
            loginService.signup(username, password, passToken)
        }
    }

    override fun login(
        userIdentity: String,
        password: String,
    ): Flow<Resource<AuthUser>> {
        return wrappedResourceFlow {
            loginService.login(userIdentity, password)
        }
    }

    override fun resetPassword(
        password: String,
        passToken: String,
    ): Flow<Resource<AuthUser>> {
        return wrappedResourceFlow {
            loginService.resetPassword(password, passToken)
        }
    }
}
