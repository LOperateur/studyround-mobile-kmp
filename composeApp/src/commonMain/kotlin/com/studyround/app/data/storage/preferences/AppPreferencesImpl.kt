package com.studyround.app.data.storage.preferences

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.set
import com.studyround.app.data.model.local.dto.UserEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class AppPreferencesImpl(private val settings: ObservableSettings) : AppPreferences {

    override val userId: Int?
        get() = settings.getIntOrNull(KEY_USER_ID)

    override val email: String?
        get() = settings.getStringOrNull(KEY_EMAIL)

    override val username: String?
        get() = settings.getStringOrNull(KEY_USERNAME)

    override val avatarUrl: String?
        get() = settings.getStringOrNull(KEY_AVATAR_URL)

    // Save user profile data
    override fun saveProfile(user: UserEntity) {
        settings[KEY_USER_ID] = user.id
        settings[KEY_EMAIL] = user.email

        user.username?.let { settings[KEY_USERNAME] = it }
        user.profileImageUrl?.let { settings[KEY_AVATAR_URL] = it }
    }

    override val darkMode: Boolean?
        get() = settings.getBooleanOrNull(KEY_DARK_MODE)

    override fun observeDarkMode(): Flow<Boolean?> {
        return callbackFlow {
            val listener = settings.addBooleanOrNullListener(KEY_DARK_MODE) {
                trySend(it)
            }
            awaitClose {
                listener.deactivate()
            }
        }.distinctUntilChanged()
    }

    override fun setDarkMode(isDarkMode: Boolean?) {
        settings[KEY_DARK_MODE] = isDarkMode
    }

    override val isCarouselViewed: Boolean
        get() = true // TODO: Update

    override fun setCarouselViewed() {
        settings[KEY_IS_CAROUSEL_VIEWED] = true
    }

    override val shouldDisplaySurveyScreen: Boolean
        get() = settings.getBoolean(SHOULD_DISPLAY_SURVEY_SCREEN, true)

    override fun setShouldDisplaySurveyScreen(shouldDisplay: Boolean) {
        settings[SHOULD_DISPLAY_SURVEY_SCREEN] = shouldDisplay
    }

    override fun clear() {
        settings.clear()
    }

    companion object {
        const val KEY_USER_ID = "key_user_id"
        const val KEY_EMAIL = "key_email"
        const val KEY_USERNAME = "key_username"
        const val KEY_AVATAR_URL = "key_avatar_url"
        const val KEY_DARK_MODE = "key_dark_mode"
        const val KEY_IS_CAROUSEL_VIEWED = "key_is_carousel_viewed"
        const val SHOULD_DISPLAY_SURVEY_SCREEN = "key_display_survey_screen"
    }
}
