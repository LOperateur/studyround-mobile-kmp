package com.studyround.app.platform

import com.studyround.app.platform.utils.IosPlatform
import com.studyround.app.platform.utils.NetworkHelper
import com.studyround.app.platform.utils.Platform

class IosApplicationComponent(
    override val networkHelper: NetworkHelper
) : SharedApplicationComponent {
    override val platform: Platform = IosPlatform()
}
