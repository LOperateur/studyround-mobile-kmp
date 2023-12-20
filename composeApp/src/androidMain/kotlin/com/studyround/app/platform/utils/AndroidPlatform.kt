package com.studyround.app.platform.utils

import android.os.Build
import com.studyround.app.BuildConfig

class AndroidPlatform : Platform {
    override val deviceName: String = "Android ${Build.VERSION.SDK_INT}"
    override val baseApiUrl: String = BuildConfig.BASE_API_URL
    override val splashScreenDelay: Long = 500
}
