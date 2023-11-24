package com.studyround.app.platform.utils

import platform.UIKit.UIDevice

class IosBuildTargetInfo : BuildTargetInfo {
    override val deviceName: String
        get() = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}
