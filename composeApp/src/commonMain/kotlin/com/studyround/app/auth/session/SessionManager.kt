package com.studyround.app.auth.session

import com.studyround.app.auth.model.AuthType
import com.studyround.app.data.remote.dto.AccessToken
import com.studyround.app.data.remote.dto.User
import com.studyround.app.platform.ui.PlatformContext
import com.studyround.app.service.data.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SessionManager {
    val isSignedIn: StateFlow<Boolean>

    fun signUp(type: AuthType): Flow<Resource<User>>

    fun login(type: AuthType): Flow<Resource<User>>

    suspend fun logout()

    fun reset(password: String): Flow<Resource<User>>

    fun refreshToken(refreshToken: String): Flow<Resource<AccessToken>>
}
