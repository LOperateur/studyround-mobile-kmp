package com.studyround.app.platform

import com.studyround.app.platform.components.SampleComponent

interface SharedPlatform {
    val sampleComponent: SampleComponent
    val deviceName: String
    // Add more shared platform-specific values or components here...
}
