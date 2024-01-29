package com.studyround.app.utils

import androidx.compose.runtime.Composable
import com.studyround.app.MR
import com.studyround.app.platform.ui.PlatformContext
import com.studyround.app.platform.ui.localizedString
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

/**
 * A wrapper class representing a string in the application. It can either be a dynamic text
 * string or a predefined string from [AppStrings]. When attempting to be rendered, the dynamic
 * text string is considered first before defaulting to the predefined app string.
 *
 * @property dynamicText The dynamic text string, if applicable.
 * @property appString The predefined string constant from [AppStrings], if applicable.
 */
data class AppString(
    val dynamicText: String?,
    val appString: AppStrings?,
) {
    constructor(dynamicText: String) : this(dynamicText = dynamicText, appString = null)
    constructor(appString: AppStrings) : this(appString = appString, dynamicText = null)
}

/**
 * Enum class representing predefined string constants used in the application.
 * This includes various error messages, alerts, and success messages.
 */
enum class AppStrings {
    // Errors
    BLANK_EMAIL_ERROR,
    INVALID_EMAIL_ERROR,
    BLANK_USERNAME_ERROR,
    INVALID_USERNAME_ERROR,
    LONG_USERNAME_ERROR,
    EMPTY_PASSWORD_ERROR,
    SHORT_PASSWORD_ERROR,
    ACCEPT_T_AND_C_ERROR,
    SOMETHING_WRONG,

    // Alerts/Messages
    SUCCESS;
}

/**
 * Sealed class representing a wrapper for string resources. It can either be a string resource
 * reference ([Res]) or a plural resource reference ([Plural]).
 */
sealed class StringResWrapper {
    data class Res(val resId: StringResource) : StringResWrapper()
    data class Plural(val resId: PluralsResource) : StringResWrapper()
}

/**
 * Retrieves the string representation of the [AppString]. If the [AppString] has dynamic text, it returns that text,
 * otherwise, it fetches the corresponding string resource based on the [AppString] property.
 *
 * @param context The platform context used for localization.
 * @param quantity The quantity for pluralization, if applicable.
 * @param args Additional format arguments for the string resource.
 * @return The localized string.
 * @throws Exception if the AppString is not defined.
 */
fun AppString.getString(context: PlatformContext, quantity: Int = 0, vararg args: Any): String {
    return dynamicText ?: run {
        when (val resource = appString?.stringRes) {
            is StringResWrapper.Res -> localizedString(context, resource.resId, *args)
            is StringResWrapper.Plural -> localizedString(context, resource.resId, quantity, *args)
            else -> throw Exception("App String not defined")
        }
    }
}

/**
 * Composable function to retrieve the string representation of the [AppString] in a Compose context.
 * If the [AppString] has dynamic text, it returns that text,
 * otherwise, it fetches the corresponding string resource based on the [AppString] property.
 *
 * @param quantity The quantity for pluralization, if applicable.
 * @param args Additional format arguments for the string resource.
 * @return The localized string.
 * @throws Exception if the AppString is not defined.
 */
@Composable
fun AppString.getString(quantity: Int = 0, vararg args: Any): String {
    return dynamicText ?: run {
        when (val resource = appString?.stringRes) {
            is StringResWrapper.Res -> stringResource(resource.resId, *args)
            is StringResWrapper.Plural -> stringResource(resource.resId, quantity, *args)
            else -> throw Exception("App String not defined")
        }
    }
}

/**
 * Extension property for [AppStrings] to get the corresponding [StringResWrapper] resource.
 */
private val AppStrings.stringRes: StringResWrapper
    get() = when (this) {
        AppStrings.BLANK_EMAIL_ERROR -> StringResWrapper.Res(MR.strings.blank_email_warning)
        AppStrings.INVALID_EMAIL_ERROR -> StringResWrapper.Res(MR.strings.invalid_email)
        AppStrings.BLANK_USERNAME_ERROR -> StringResWrapper.Res(MR.strings.blank_username_warning)
        AppStrings.INVALID_USERNAME_ERROR -> StringResWrapper.Res(MR.strings.invalid_username)
        AppStrings.LONG_USERNAME_ERROR -> StringResWrapper.Res(MR.strings.long_username_warning)
        AppStrings.EMPTY_PASSWORD_ERROR -> StringResWrapper.Res(MR.strings.empty_password_warning)
        AppStrings.SHORT_PASSWORD_ERROR -> StringResWrapper.Res(MR.strings.short_password_warning)
        AppStrings.ACCEPT_T_AND_C_ERROR -> StringResWrapper.Res(MR.strings.accept_terms_of_use_prompt)
        AppStrings.SOMETHING_WRONG -> StringResWrapper.Res(MR.strings.something_wrong)

        AppStrings.SUCCESS -> StringResWrapper.Res(MR.strings.success_alert)
    }
