package com.studyround.app.platform

import com.russhwolf.settings.Settings
import com.studyround.app.platform.utils.NetworkHelper
import com.studyround.app.platform.utils.BuildTargetInfo

typealias Credentials = Settings

interface SharedApplicationComponent {
    val buildTargetInfo: BuildTargetInfo
    val networkHelper: NetworkHelper
    val settings: Settings
    val credentials: Credentials
    // Add more shared platform-specific components here...
}
