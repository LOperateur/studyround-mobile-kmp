package com.studyround.app.platform

import com.studyround.app.platform.utils.NetworkHelper
import com.studyround.app.platform.utils.BuildTargetInfo

interface SharedApplicationComponent {
    val buildTargetInfo: BuildTargetInfo
    val networkHelper: NetworkHelper
    // Add more shared platform-specific components here...
}
