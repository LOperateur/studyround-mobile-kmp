package com.studyround.app.platform.utils

import android.os.Build

class AndroidPlatform : Platform {
    override val deviceName: String = "Android ${Build.VERSION.SDK_INT}"
}
