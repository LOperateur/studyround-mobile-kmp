package com.studyround.app.platform

import com.studyround.app.platform.components.SampleComponent
import com.studyround.app.platform.utils.NetworkHelper
import platform.UIKit.UIDevice

/**
 * Called from iOS App in Swift
 */
class IosPlatform(
    override val sampleComponent: SampleComponent,
    override val networkHelper: NetworkHelper,
) : SharedPlatform {
    override val deviceName: String
        get() = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}
