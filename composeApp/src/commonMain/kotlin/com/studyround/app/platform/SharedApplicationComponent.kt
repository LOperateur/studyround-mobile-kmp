package com.studyround.app.platform

import com.studyround.app.platform.utils.NetworkHelper
import com.studyround.app.platform.utils.Platform

interface SharedApplicationComponent {
    val platform: Platform
    val networkHelper: NetworkHelper
    // Add more shared platform-specific components here...
}