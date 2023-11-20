package com.studyround.app.platform

import com.studyround.app.platform.components.SampleComponent
import com.studyround.app.platform.utils.NetworkHelper

interface SharedPlatform {
    val sampleComponent: SampleComponent
    val networkHelper: NetworkHelper
    val deviceName: String
    // Add more shared platform-specific values or components here...
}
