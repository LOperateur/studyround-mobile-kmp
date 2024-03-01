package com.studyround.app.utils

import androidx.compose.runtime.Composable
import com.studyround.app.utils.StringResWrapper.PluralRes
import com.studyround.app.utils.StringResWrapper.StringRes
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.*

/**
 * A wrapper class representing a string in the application. It can either be a dynamic text
 * string or a predefined string from [AppStrings]. When attempting to be rendered, the dynamic
 * text string is considered first before defaulting to the predefined app string.
 *
 * @property dynamicText The dynamic text string, if applicable.
 * @property appString The predefined string constant from [AppStrings], if applicable.
 */
data class AppString(
    internal val dynamicText: String?,
    internal val appString: AppStrings?,
) {
    constructor(dynamicText: String) : this(dynamicText = dynamicText, appString = null)
    constructor(appString: AppStrings) : this(appString = appString, dynamicText = null)

    companion object {
        fun textOrSuccess(dynamicText: String?): AppString =
            AppString(dynamicText, AppStrings.SUCCESS)

        fun textOrError(dynamicText: String?): AppString =
            AppString(dynamicText, AppStrings.SOMETHING_WRONG)
    }
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
    SHORT_USERNAME_ERROR,
    LONG_USERNAME_ERROR,
    EMPTY_PASSWORD_ERROR,
    SHORT_PASSWORD_ERROR,
    NO_MATCH_PASSWORD_ERROR,
    ACCEPT_T_AND_C_ERROR,
    OTP_LIMIT_ERROR,
    SOMETHING_WRONG,

    // Prompts/Text
    EMAIL_CONFIRMATION,
    OTP_VERIFICATION,

    // Alerts/Messages
    OTP_SENT_ALERT,
    SUCCESS;
}

/**
 * Sealed class representing a wrapper for string resources. It can either be a string resource
 * reference ([StringRes]) or a plural resource reference ([PluralRes]).
 */
sealed class StringResWrapper {
    data class StringRes(val resId: StringResource) : StringResWrapper()
    data class PluralRes(val resId: StringResource) : StringResWrapper()
}

/**
 * Retrieves the string representation of the [AppString]. If the [AppString] has dynamic text, it returns that text,
 * otherwise, it fetches the corresponding string resource based on the [AppString] property.
 *
 * @param quantity The quantity for pluralization, if applicable.
 * @param args Additional format arguments for the string resource.
 * @return The localized string.
 * @throws Exception if the AppString is not defined.
 */
suspend fun AppString.loadString(quantity: Int = 0, vararg args: Any): String {
    return dynamicText ?: run {
        when (val resource = appString?.stringRes) {
            is StringRes -> org.jetbrains.compose.resources.getString(resource.resId, *args)
            is PluralRes -> org.jetbrains.compose.resources.getString(resource.resId, quantity, *args)
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
            is StringRes -> stringResource(resource.resId, *args)
            is PluralRes -> stringResource(resource.resId, quantity, *args)
            else -> throw Exception("App String not defined")
        }
    }
}

/**
 * Extension property for [AppStrings] to get the corresponding [StringResWrapper] resource.
 */
private val AppStrings.stringRes: StringResWrapper
    get() = when (this) {
        AppStrings.BLANK_EMAIL_ERROR -> StringRes(Res.string.blank_email_warning)
        AppStrings.INVALID_EMAIL_ERROR -> StringRes(Res.string.invalid_email)
        AppStrings.BLANK_USERNAME_ERROR -> StringRes(Res.string.blank_username_warning)
        AppStrings.INVALID_USERNAME_ERROR -> StringRes(Res.string.invalid_username)
        AppStrings.SHORT_USERNAME_ERROR -> StringRes(Res.string.short_username_warning)
        AppStrings.LONG_USERNAME_ERROR -> StringRes(Res.string.long_username_warning)
        AppStrings.EMPTY_PASSWORD_ERROR -> StringRes(Res.string.empty_password_warning)
        AppStrings.SHORT_PASSWORD_ERROR -> StringRes(Res.string.short_password_warning)
        AppStrings.NO_MATCH_PASSWORD_ERROR -> StringRes(Res.string.match_password_warning)
        AppStrings.ACCEPT_T_AND_C_ERROR -> StringRes(Res.string.accept_terms_of_use_prompt)
        AppStrings.OTP_LIMIT_ERROR -> StringRes(Res.string.otp_limit_error)
        AppStrings.SOMETHING_WRONG -> StringRes(Res.string.something_wrong)

        AppStrings.EMAIL_CONFIRMATION -> StringRes(Res.string.email_verification)
        AppStrings.OTP_VERIFICATION -> StringRes(Res.string.otp_confirmation)

        AppStrings.OTP_SENT_ALERT -> StringRes(Res.string.otp_sent_alert)
        AppStrings.SUCCESS -> StringRes(Res.string.success_alert)
    }
