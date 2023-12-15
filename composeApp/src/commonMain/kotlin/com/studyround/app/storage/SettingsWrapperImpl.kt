package com.studyround.app.storage

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.set

class SettingsWrapperImpl(private val settings: ObservableSettings): SettingsWrapper {
    override val darkMode: Boolean?
        get() = settings.getBooleanOrNull(KEY_DARK_MODE)

    override fun setDarkMode(isDarkMode: Boolean?) {
        settings[KEY_DARK_MODE] = isDarkMode
    }

    override val lastSavedPassToken: String?
        get() = settings.getStringOrNull(KEY_LAST_PASS_TOKEN)

    override fun setLastSavedPassToken(passToken: String?) {
        settings[KEY_LAST_PASS_TOKEN] = passToken
    }

    companion object {
        const val KEY_DARK_MODE = "key_dark_mode"
        const val KEY_LAST_PASS_TOKEN = "key_last_pass_token"
    }
}
