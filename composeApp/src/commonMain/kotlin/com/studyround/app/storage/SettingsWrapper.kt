package com.studyround.app.storage

interface SettingsWrapper {
    // TODO: Make this an observable flow
    val darkMode: Boolean?
    fun setDarkMode(isDarkMode: Boolean?)
}
