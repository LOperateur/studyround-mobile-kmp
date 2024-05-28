package com.studyround.app.data.auth.email

import com.studyround.app.data.model.remote.dto.AuthUser
import com.studyround.app.data.resource.Resource
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
}
