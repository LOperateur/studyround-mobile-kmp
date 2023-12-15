package com.studyround.app.auth.session

import com.studyround.app.auth.model.AuthProviderType
import kotlinx.coroutines.flow.StateFlow

interface SessionManager {
    val isSignedIn: StateFlow<Boolean>

    suspend fun signUp(type: AuthProviderType)

    suspend fun login(type: AuthProviderType)

    suspend fun logout()

    fun reset(password: String)

    fun refreshToken()
}
