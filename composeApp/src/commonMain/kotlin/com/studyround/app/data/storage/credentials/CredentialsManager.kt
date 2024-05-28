package com.studyround.app.data.storage.credentials

import com.studyround.app.data.auth.model.AuthCredentials
import kotlinx.coroutines.flow.StateFlow

interface CredentialsManager {
    val hasValidCredentials: StateFlow<Boolean>

    fun hasValidCredentials(): Boolean

    fun getAuthToken(): String?

    fun getRefreshToken(): String?

    fun saveCredentials(authCredentials: AuthCredentials)

    fun clearCredentials()
}
