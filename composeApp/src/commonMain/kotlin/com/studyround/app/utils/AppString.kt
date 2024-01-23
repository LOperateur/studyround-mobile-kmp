package com.studyround.app.utils

import androidx.compose.runtime.Composable
import com.studyround.app.MR
import com.studyround.app.platform.ui.PlatformContext
import com.studyround.app.platform.ui.localizedString
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

sealed class AppString {
    data class Res(val resId: StringResource) : AppString()
    data class Plural(val resId: PluralsResource, var quantity: Int = 0) : AppString()
    data class Text(val text: String) : AppString()
}

fun AppString.getString(context: PlatformContext, vararg args: Any): String {
    return when (this) {
        is AppString.Res -> {
            localizedString(context, resId, *args)
        }

        is AppString.Plural -> {
            localizedString(context, resId, quantity, *args)
        }

        is AppString.Text -> text
    }
}

@Composable
fun AppString.getString(vararg args: Any): String {
    return when (this) {
        is AppString.Res -> {
            stringResource(resId, *args)
        }

        is AppString.Plural -> {
            stringResource(resId, quantity, *args)
        }

        is AppString.Text -> text
    }
}

enum class StudyRoundStrings(val appString: AppString) {
    // Errors
    BLANK_EMAIL_ERROR(AppString.Res(MR.strings.blank_email_warning)),
    INVALID_EMAIL_ERROR(AppString.Res(MR.strings.invalid_email)),
    BLANK_USERNAME_ERROR(AppString.Res(MR.strings.blank_username_warning)),
    INVALID_USERNAME_ERROR(AppString.Res(MR.strings.invalid_username)),
    LONG_USERNAME_ERROR(AppString.Res(MR.strings.long_username_warning)),
    EMPTY_PASSWORD_ERROR(AppString.Res(MR.strings.empty_password_warning)),
    SHORT_PASSWORD_ERROR(AppString.Res(MR.strings.short_password_warning)),
    ACCEPT_T_AND_C_ERROR(AppString.Res(MR.strings.accept_terms_of_use_prompt)),

    // Alerts/Messages
    SUCCESS(AppString.Res(MR.strings.success_alert));

    fun withQuantity(quantity: Int): StudyRoundStrings {
        (appString as? AppString.Plural)?.quantity = quantity
        return this
    }
}
