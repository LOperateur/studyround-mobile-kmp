package com.studyround.app.platform.utils

import android.os.Build

class AndroidBuildTargetInfo : BuildTargetInfo {
    override val deviceName: String = "Android ${Build.VERSION.SDK_INT}"
}
