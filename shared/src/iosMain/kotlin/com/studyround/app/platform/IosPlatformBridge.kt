package com.studyround.app.platform

import com.studyround.app.platform.components.SamplePlatformComponent
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "IosPlatformBridge")
class IosPlatformBridge(override var samplePlatformComponent: SamplePlatformComponent) : PlatformBridge

fun providesIosPlatformBridge(samplePlatformComponent: SamplePlatformComponent): IosPlatformBridge {
    return IosPlatformBridge(samplePlatformComponent = samplePlatformComponent)
}
