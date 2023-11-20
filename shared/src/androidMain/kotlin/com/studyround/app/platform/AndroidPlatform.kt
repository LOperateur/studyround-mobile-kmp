package com.studyround.app.platform

import android.content.Context
import android.os.Build
import android.util.Log
import com.studyround.app.platform.components.SampleComponent
import com.studyround.app.platform.utils.AndroidNetworkHelper
import com.studyround.app.platform.utils.NetworkHelper

class AndroidPlatform(context: Context) : SharedPlatform {
    override val sampleComponent: SampleComponent = SampleAndroidComponent()
    override val networkHelper: NetworkHelper = AndroidNetworkHelper(context)
    override val deviceName: String = "Android ${Build.VERSION.SDK_INT}"
}

class SampleAndroidComponent : SampleComponent {
    override fun sayHello(name: String) {
        Log.d("Sample", "Hello from Android $name 👋!")
    }

    override fun returnGreeting(name: String): String {
        return "Hi from Android $name!"
    }
}
