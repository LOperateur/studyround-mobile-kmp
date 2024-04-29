package com.studyround.app.platform.utils

import platform.Foundation.NSBundle
import platform.UIKit.UIDevice

class IosPlatform : Platform {
    override val deviceName: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val baseApiUrl: String = NSBundle.mainBundle.infoDictionary?.get("BASE_API_URL") as String
    override val splashScreenDelay: Long = 500
}
