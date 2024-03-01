package com.studyround.app.storage

interface AppPreferences {
    // TODO: Make this an observable flow
    val darkMode: Boolean?
    fun setDarkMode(isDarkMode: Boolean?)

    val isCarouselViewed: Boolean
    fun setCarouselViewed()

    val shouldDisplaySurveyScreen: Boolean
    fun setDisplaySurveyScreen(shouldDisplay: Boolean)
}
