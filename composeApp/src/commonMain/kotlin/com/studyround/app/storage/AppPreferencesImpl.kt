package com.studyround.app.storage

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.set

class AppPreferencesImpl(private val settings: ObservableSettings) : AppPreferences {
    override val darkMode: Boolean?
        get() = settings.getBooleanOrNull(KEY_DARK_MODE)

    override fun setDarkMode(isDarkMode: Boolean?) {
        settings[KEY_DARK_MODE] = isDarkMode
    }

    override val isCarouselViewed: Boolean
        get() = true // TODO: Update

    override fun setCarouselViewed() {
        settings[KEY_IS_CAROUSEL_VIEWED] = true
    }

    override val shouldDisplaySurveyScreen: Boolean
        get() = settings.getBoolean(DISPLAY_SURVEY_SCREEN, true)

    override fun setDisplaySurveyScreen(shouldDisplay: Boolean) {
        settings[DISPLAY_SURVEY_SCREEN] = shouldDisplay
    }

    companion object {
        const val KEY_DARK_MODE = "key_dark_mode"
        const val KEY_IS_CAROUSEL_VIEWED = "key_is_carousel_viewed"
        const val DISPLAY_SURVEY_SCREEN = "key_display_survey_screen"
    }
}
