package com.studyround.app.platform.utils

import platform.UIKit.UIDevice

class IosPlatform : Platform {
    override val deviceName: String
        get() = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}
