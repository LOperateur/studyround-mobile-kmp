package com.studyround.app.platform

import android.content.Context
import com.studyround.app.platform.utils.AndroidNetworkHelper
import com.studyround.app.platform.utils.AndroidBuildTargetInfo
import com.studyround.app.platform.utils.NetworkHelper
import com.studyround.app.platform.utils.BuildTargetInfo

class AndroidApplicationComponent(context: Context) : SharedApplicationComponent {
    override val buildTargetInfo: BuildTargetInfo = AndroidBuildTargetInfo()
    override val networkHelper: NetworkHelper = AndroidNetworkHelper(context)
}
