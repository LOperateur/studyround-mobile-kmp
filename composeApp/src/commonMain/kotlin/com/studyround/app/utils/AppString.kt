package com.studyround.app.utils

import androidx.compose.runtime.Composable
import com.studyround.app.utils.StringResWrapper.PluralRes
import com.studyround.app.utils.StringResWrapper.StringRes
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.pluralStringResource
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
    private val dynamicText: String?,
    private val appString: AppStrings?,
) {
    constructor(dynamicText: String) : this(dynamicText = dynamicText, appString = null)
    constructor(appString: AppStrings) : this(appString = appString, dynamicText = null)

    companion object {
        fun textOrSuccess(dynamicText: String?): AppString =
            AppString(dynamicText, AppStrings.SUCCESS)

        fun textOrError(dynamicText: String?): AppString =
            AppString(dynamicText, AppStrings.SOMETHING_WRONG)
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
    suspend fun loadString(vararg args: Any, quantity: Int = 0): String {
        return dynamicText ?: run {
            when (val resource = appString?.stringRes) {
                is StringRes -> org.jetbrains.compose.resources.getString(resource.resId, *args)
                is PluralRes -> org.jetbrains.compose.resources.getPluralString(resource.resId, quantity, *args)
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
    fun getString(vararg args: Any, quantity: Int = 0): String {
        return dynamicText ?: run {
            when (val resource = appString?.stringRes) {
                is StringRes -> stringResource(resource.resId, *args)
                is PluralRes -> pluralStringResource(resource.resId, quantity, *args)
                else -> throw Exception("App String not defined")
            }
        }
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
    GOOGLE_SIGN_IN_ERROR,
    GOOGLE_SIGN_UP_ERROR,
    NO_OCCUPATION_ERROR,
    GENERIC_BLANK_DATA_ERROR,
    SOMETHING_WRONG,
    NO_CONNECTIVITY,

    // Prompts/Text
    EMAIL_CONFIRMATION,
    OTP_VERIFICATION,
    STUDENT_OCCUPATION,
    PROFESSIONAL_OCCUPATION,
    PRIMARY_GRADE,
    SECONDARY_GRADE,
    TERTIARY_GRADE,
    POST_GRADUATE_GRADE,
    GOOGLE,
    FACEBOOK,
    INSTAGRAM,
    EMAIL,
    FRIEND_RECOMMENDATION,
    OTHER,
    MENU_PROFILE,
    MENU_RESULTS,
    MENU_FAQS,
    MENU_TRANSACTIONS,

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
    data class PluralRes(val resId: PluralStringResource) : StringResWrapper()
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
        AppStrings.GOOGLE_SIGN_IN_ERROR -> StringRes(Res.string.google_sign_in_error)
        AppStrings.GOOGLE_SIGN_UP_ERROR -> StringRes(Res.string.google_sign_up_error)
        AppStrings.NO_OCCUPATION_ERROR -> StringRes(Res.string.no_occupation_error)
        AppStrings.GENERIC_BLANK_DATA_ERROR -> StringRes(Res.string.generic_blank_data_error)
        AppStrings.SOMETHING_WRONG -> StringRes(Res.string.something_wrong)
        AppStrings.NO_CONNECTIVITY -> StringRes(Res.string.no_connectivity_error)

        AppStrings.EMAIL_CONFIRMATION -> StringRes(Res.string.email_verification)
        AppStrings.OTP_VERIFICATION -> StringRes(Res.string.otp_confirmation)
        AppStrings.STUDENT_OCCUPATION -> StringRes(Res.string.student_occupation)
        AppStrings.PROFESSIONAL_OCCUPATION -> StringRes(Res.string.professional_occupation)
        AppStrings.PRIMARY_GRADE -> StringRes(Res.string.primary_grade)
        AppStrings.SECONDARY_GRADE -> StringRes(Res.string.secondary_grade)
        AppStrings.TERTIARY_GRADE -> StringRes(Res.string.tertiary_grade)
        AppStrings.POST_GRADUATE_GRADE -> StringRes(Res.string.post_graduate_grade)
        AppStrings.GOOGLE -> StringRes(Res.string.google)
        AppStrings.FACEBOOK -> StringRes(Res.string.facebook)
        AppStrings.INSTAGRAM -> StringRes(Res.string.instagram)
        AppStrings.EMAIL -> StringRes(Res.string.email)
        AppStrings.FRIEND_RECOMMENDATION -> StringRes(Res.string.friend_recommendation)
        AppStrings.OTHER -> StringRes(Res.string.other)
        AppStrings.MENU_PROFILE -> StringRes(Res.string.menu_profile)
        AppStrings.MENU_RESULTS -> StringRes(Res.string.menu_results)
        AppStrings.MENU_FAQS -> StringRes(Res.string.menu_faqs)
        AppStrings.MENU_TRANSACTIONS -> StringRes(Res.string.menu_transactions)

        AppStrings.OTP_SENT_ALERT -> StringRes(Res.string.otp_sent_alert)
        AppStrings.SUCCESS -> StringRes(Res.string.success_alert)
    }
