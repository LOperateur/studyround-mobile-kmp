package com.studyround.app.platform

import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import com.studyround.app.platform.utils.IosBuildTargetInfo
import com.studyround.app.platform.utils.NetworkHelper
import com.studyround.app.platform.utils.BuildTargetInfo
import platform.Foundation.NSUserDefaults

class IosApplicationComponent(
    override val networkHelper: NetworkHelper,
    private val userDefaults: NSUserDefaults,
) : SharedApplicationComponent {
    override val buildTargetInfo: BuildTargetInfo = IosBuildTargetInfo()
    override val settings: Settings = NSUserDefaultsSettings(userDefaults)
    @OptIn(ExperimentalSettingsImplementation::class)
    override val credentials: Credentials = KeychainSettings("com.operator.studyround.secrets")
}
