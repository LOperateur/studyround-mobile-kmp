package com.studyround.app.storage

import com.studyround.app.auth.model.AuthCredentials
import kotlinx.coroutines.flow.StateFlow

interface CredentialsManager {
    val hasValidCredentials: StateFlow<Boolean>

    fun getAuthToken(): String?

    fun getRefreshToken(): String?

    fun saveCredentials(authCredentials: AuthCredentials)

    fun clearCredentials()
}
