package com.studyround.app.platform

import com.studyround.app.platform.utils.NetworkHelper
import platform.Foundation.NSUserDefaults

class IosApplicationComponent(
    val networkHelper: NetworkHelper,
    val userDefaults: NSUserDefaults,
) : SharedApplicationComponent
