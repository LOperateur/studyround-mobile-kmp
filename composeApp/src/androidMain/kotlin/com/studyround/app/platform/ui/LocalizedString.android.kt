package com.studyround.app.platform.ui

import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.format

actual fun localizedString(context: PlatformContext, string: StringResource, vararg args: Any): String {
    return string.format(*args).toString(context = context.androidContext)
}

actual fun localizedString(context: PlatformContext, string: PluralsResource, quantity: Int, vararg args: Any): String {
    return string.format(quantity, *args).toString(context = context.androidContext)
}
