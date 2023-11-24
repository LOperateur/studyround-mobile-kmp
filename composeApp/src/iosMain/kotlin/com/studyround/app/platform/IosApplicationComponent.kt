package com.studyround.app.platform

import com.studyround.app.platform.utils.IosBuildTargetInfo
import com.studyround.app.platform.utils.NetworkHelper
import com.studyround.app.platform.utils.BuildTargetInfo

class IosApplicationComponent(
    override val networkHelper: NetworkHelper
) : SharedApplicationComponent {
    override val buildTargetInfo: BuildTargetInfo = IosBuildTargetInfo()
}
