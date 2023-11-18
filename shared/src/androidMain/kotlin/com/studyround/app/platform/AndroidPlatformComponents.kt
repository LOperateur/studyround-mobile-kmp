package com.studyround.app.platform

import android.os.Build
import android.util.Log
import com.studyround.app.platform.components.SampleComponent

class AndroidPlatformComponents : PlatformComponents {
    override val sampleComponent: SampleComponent = SampleAndroidComponent()
    override val version: String = "Android ${Build.VERSION.SDK_INT}"
}

class SampleAndroidComponent : SampleComponent {
    override fun sayHello(name: String) {
        Log.d("Sample", "Hello from Android $name 👋!")
    }

    override fun returnGreeting(name: String): String {
        return "Hi from Android $name!"
    }
}
