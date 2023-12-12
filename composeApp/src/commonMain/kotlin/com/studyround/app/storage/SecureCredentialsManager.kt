package com.studyround.app.storage

import com.studyround.app.auth.model.AuthCredentials
import com.studyround.app.di.Credentials
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SecureCredentialsManager(private val credentials: Credentials) : CredentialsManager {
    override val hasValidCredentials: StateFlow<Boolean>
        get() {
            val hasValidCredentials = MutableStateFlow(credentials.getStringOrNull(KEY_AUTH_TOKEN) != null)
            return hasValidCredentials.asStateFlow()
        }

    override fun getAuthToken(): String? {
        return credentials.getStringOrNull(KEY_AUTH_TOKEN)
    }

    override fun getRefreshToken(): String? {
        return credentials.getStringOrNull(KEY_REFRESH_TOKEN)
    }

    override fun saveCredentials(authCredentials: AuthCredentials) {
        credentials.putString(KEY_AUTH_TOKEN, authCredentials.authToken)
        credentials.putString(KEY_REFRESH_TOKEN, authCredentials.refreshToken)
    }

    override fun clearCredentials() {
        credentials.clear()
    }

    companion object {
        const val KEY_AUTH_TOKEN = "key_auth_token"
        const val KEY_REFRESH_TOKEN = "key_refresh_token"
    }
}
