package com.studyround.app.auth.email

import com.studyround.app.service.data.resource.Resource
import kotlinx.coroutines.flow.Flow

interface EmailAuthProvider {
    fun signup(
        username: String,
        password: String,
        passToken: String?,
    ): Flow<Resource<Unit>>

    fun login(
        userIdentity: String,
        password: String,
    ): Flow<Resource<Unit>>

    fun resetPassword(
        password: String,
        passToken: String?,
    ): Flow<Resource<Unit>>

    fun refreshToken(
        refreshToken: String,
    ): Flow<Resource<Unit>>
}
