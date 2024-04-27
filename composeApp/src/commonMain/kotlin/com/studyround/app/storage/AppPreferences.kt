package com.studyround.app.storage

import com.studyround.app.data.remote.dto.User

interface AppPreferences {
    val userId: Int?

    val email: String?

    val username: String?

    val avatarUrl: String?

    fun saveProfile(user: User)

    // TODO: Make this an observable flow
    val darkMode: Boolean?
    fun setDarkMode(isDarkMode: Boolean?)

    val isCarouselViewed: Boolean
    fun setCarouselViewed()

    val shouldDisplaySurveyScreen: Boolean
    fun setShouldDisplaySurveyScreen(shouldDisplay: Boolean)

    fun clear()
}
