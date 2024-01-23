package com.studyround.app.platform.ui

import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource

expect fun localizedString(context: PlatformContext, string: StringResource, vararg args: Any): String

expect fun localizedString(context: PlatformContext, string: PluralsResource, quantity: Int, vararg args: Any): String
