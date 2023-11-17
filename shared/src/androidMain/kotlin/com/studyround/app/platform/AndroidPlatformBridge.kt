package com.studyround.app.platform

import android.util.Log
import com.studyround.app.platform.components.SamplePlatformComponent

class AndroidBridge : PlatformBridge {
    override var samplePlatformComponent: SamplePlatformComponent = SampleAndroidComponent()
}

class SampleAndroidComponent : SamplePlatformComponent {
    override fun sayHello(name: String) {
        Log.d("Sample", "Hello from Android $name 👋!")
    }

    override fun returnGreeting(name: String): String {
        return "Hi from Android $name!"
    }
}
