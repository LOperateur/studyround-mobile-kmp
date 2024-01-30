package com.studyround.app.auth.email

import com.studyround.app.data.remote.dto.AccessToken
import com.studyround.app.data.remote.dto.AuthUser
import com.studyround.app.service.data.resource.Resource
import kotlinx.coroutines.flow.Flow

interface EmailAuthProvider {
    fun signup(
        username: String,
        password: String,
        passToken: String,
    ): Flow<Resource<AuthUser>>

    fun login(
        userIdentity: String,
        password: String,
    ): Flow<Resource<AuthUser>>

    fun resetPassword(
        password: String,
        passToken: String,
    ): Flow<Resource<AuthUser>>

    fun refreshToken(
        refreshToken: String,
    ): Flow<Resource<AccessToken>>
}
