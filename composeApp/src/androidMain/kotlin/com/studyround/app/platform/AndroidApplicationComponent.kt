package com.studyround.app.platform

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import com.studyround.app.platform.utils.AndroidNetworkHelper
import com.studyround.app.platform.utils.AndroidBuildTargetInfo
import com.studyround.app.platform.utils.NetworkHelper
import com.studyround.app.platform.utils.BuildTargetInfo
import com.studyround.app.storage.Preferences.createCredentialsPrefs
import com.studyround.app.storage.Preferences.createSettingsPrefs

class AndroidApplicationComponent(context: Context) : SharedApplicationComponent {
    override val buildTargetInfo: BuildTargetInfo = AndroidBuildTargetInfo()
    override val networkHelper: NetworkHelper = AndroidNetworkHelper(context)
    override val settings: Settings = SharedPreferencesSettings(createSettingsPrefs(context),true)
    override val credentials: Credentials = SharedPreferencesSettings(createCredentialsPrefs(context), true)
}
