package com.studyround.app.storage

interface AppPreferences {
    // TODO: Make this an observable flow
    val darkMode: Boolean?
    fun setDarkMode(isDarkMode: Boolean?)

    val lastSavedPassToken: String?
    fun setLastSavedPassToken(passToken: String?)

    val isCarouselViewed: Boolean
    fun setCarouselViewed()
}
