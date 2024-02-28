package com.studyround.app.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtpRequest(
    @SerialName("user_identity") val userIdentity: String,
    @SerialName("type") val authType: AuthType,
    @SerialName("resend") val resend: Boolean,
)

@Serializable
enum class AuthType {
    @SerialName("auth_type_verify_email") VERIFY_EMAIL,
    @SerialName("auth_type_reset_password") RESET_PASSWORD,
}
