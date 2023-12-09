package com.studyround.app.storage

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class SettingsWrapperImpl(private val settings: Settings): SettingsWrapper {
    override val darkMode: Boolean?
        get() = settings.getBooleanOrNull(KEY_DARK_MODE)

    override fun setDarkMode(isDarkMode: Boolean?) {
        settings[KEY_DARK_MODE] = isDarkMode
    }

    companion object {
        const val KEY_DARK_MODE = "key_dark_mode"
    }
}
