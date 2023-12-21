package com.studyround.app.platform.utils

interface Platform {
    val deviceName: String
    val baseApiUrl: String
    val splashScreenDelay: Long
    // Add more platform-specific values here...
}
