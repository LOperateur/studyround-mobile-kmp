package com.studyround.app.storage

import com.studyround.app.data.remote.dto.User
import kotlinx.coroutines.flow.Flow

interface AppPreferences {
    val userId: Int?

    val email: String?

    val username: String?

    val avatarUrl: String?

    fun saveProfile(user: User)

    val darkMode: Boolean?
    fun observeDarkMode(): Flow<Boolean?>
    fun setDarkMode(isDarkMode: Boolean?)

    val isCarouselViewed: Boolean
    fun setCarouselViewed()

    val shouldDisplaySurveyScreen: Boolean
    fun setShouldDisplaySurveyScreen(shouldDisplay: Boolean)

    fun clear()
}
