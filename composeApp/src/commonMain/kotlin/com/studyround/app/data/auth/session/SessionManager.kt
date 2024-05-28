package com.studyround.app.data.auth.session

import com.studyround.app.data.auth.model.AuthCredentials
import com.studyround.app.data.auth.model.AuthType
import com.studyround.app.data.model.remote.dto.User
import com.studyround.app.data.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SessionManager {
    val isSignedIn: StateFlow<Boolean>

    fun signUp(type: AuthType): Flow<Resource<User>>

    fun login(type: AuthType): Flow<Resource<User>>

    suspend fun logout()

    fun reset(password: String, passToken: String): Flow<Resource<User>>

    fun getAuthCredentials(): AuthCredentials?

    suspend fun refreshAuthCredentials(): AuthCredentials?
}
