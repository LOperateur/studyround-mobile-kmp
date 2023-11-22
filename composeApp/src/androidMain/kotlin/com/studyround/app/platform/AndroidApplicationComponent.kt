package com.studyround.app.platform

import android.content.Context
import com.studyround.app.platform.utils.AndroidNetworkHelper
import com.studyround.app.platform.utils.AndroidPlatform
import com.studyround.app.platform.utils.NetworkHelper
import com.studyround.app.platform.utils.Platform

class AndroidApplicationComponent(context: Context) : SharedApplicationComponent {
    override val platform: Platform = AndroidPlatform()
    override val networkHelper: NetworkHelper = AndroidNetworkHelper(context)
}