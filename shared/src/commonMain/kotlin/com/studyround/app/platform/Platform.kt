package com.studyround.app.platform

import com.studyround.app.platform.components.SampleComponent

interface Platform {
    val sampleComponent: SampleComponent
    val deviceName: String
    // Add more platform-specific values or components here...
}
