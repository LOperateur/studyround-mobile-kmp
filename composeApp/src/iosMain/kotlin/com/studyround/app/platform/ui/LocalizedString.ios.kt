package com.studyround.app.platform.ui

import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.desc

actual fun localizedString(context: PlatformContext, string: StringResource, vararg args: Any): String {
    return string.desc().localized()
}

actual fun localizedString(context: PlatformContext, string: PluralsResource, quantity: Int, vararg args: Any): String {
    return string.desc(quantity).localized()
}
