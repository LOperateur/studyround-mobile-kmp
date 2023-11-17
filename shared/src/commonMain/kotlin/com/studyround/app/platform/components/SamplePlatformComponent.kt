package com.studyround.app.platform.components

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "SamplePlatformComponent")
interface SamplePlatformComponent {
    fun sayHello(name: String)
    fun returnGreeting(name: String): String
}
