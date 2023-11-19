package com.studyround.app.platform

import com.studyround.app.platform.components.SampleComponent
import platform.UIKit.UIDevice

/**
 * Called from iOS App in Swift
 */
class IosPlatform(
    override val sampleComponent: SampleComponent,
) : SharedPlatform {
    override val deviceName: String
        get() = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}
