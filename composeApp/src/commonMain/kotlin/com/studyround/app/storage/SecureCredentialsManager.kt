package com.studyround.app.storage

import com.russhwolf.settings.ObservableSettings
import com.studyround.app.auth.model.AuthCredentials
import com.studyround.app.di.Credentials
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock

class SecureCredentialsManager(
    private val credentials: Credentials,
    private val settings: ObservableSettings,
) : CredentialsManager {

    private val _hasValidCredentials = MutableStateFlow(hasValidCredentials())
    override val hasValidCredentials: StateFlow<Boolean>
        get() {
            return _hasValidCredentials.asStateFlow()
        }

    override fun hasValidCredentials(): Boolean {
        return credentials.getStringOrNull(KEY_AUTH_TOKEN) != null &&
                settings.getLongOrNull(KEY_LAST_CREDENTIALS_RESET) != null
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
        settings.putLong(KEY_LAST_CREDENTIALS_RESET, Clock.System.now().toEpochMilliseconds())

        _hasValidCredentials.update { hasValidCredentials() }
    }

    override fun clearCredentials() {
        settings.remove(KEY_LAST_CREDENTIALS_RESET)
        credentials.clear()

        _hasValidCredentials.update { false }
    }

    companion object {
        const val KEY_AUTH_TOKEN = "key_auth_token"
        const val KEY_REFRESH_TOKEN = "key_refresh_token"
        const val KEY_LAST_CREDENTIALS_RESET = "key_last_cred_reset"
    }
}
