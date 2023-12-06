package com.studyround.app.platform

import com.studyround.app.platform.auth.GoogleAuthProvider
import com.studyround.app.platform.utils.NetworkHelper
import platform.Foundation.NSUserDefaults

class IosApplicationComponent(
    val networkHelper: NetworkHelper,
    val googleAuthProvider: GoogleAuthProvider,
) : SharedApplicationComponent
