package com.studyround.app.auth.session

import com.studyround.app.auth.model.AuthProviderType
import com.studyround.app.service.data.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SessionManager {
    val isSignedIn: StateFlow<Boolean>

    fun signUp(type: AuthProviderType): Flow<Resource<Unit>>

    fun login(type: AuthProviderType): Flow<Resource<Unit>>

    suspend fun logout()

    fun reset(password: String): Flow<Resource<Unit>>

    fun refreshToken(refreshToken: String): Flow<Resource<Unit>>
}
