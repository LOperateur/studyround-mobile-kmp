package com.studyround.app.platform

import com.studyround.app.platform.components.SampleComponent
import platform.UIKit.UIDevice

/**
 * Called from iOS App in Swift
 */
class IosPlatformComponents(
    override val sampleComponent: SampleComponent,
) : PlatformComponents {
    override val version: String
        get() = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}
