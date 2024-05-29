package com.studyround.app.data.model.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id: Long,
    @SerialName("email") val email: String,
    @SerialName("username") val username: String? = null,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("other_name") val otherName: String? = null,
    @SerialName("about") val about: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("creator") val creator: Boolean = false,
    @SerialName("date_of_birth")val dateOfBirth: String? = null,
    @SerialName("occupation") val occupation: String? = null,
    @SerialName("pro_account") val proAccount: Boolean = false,
    @SerialName("profile_image_url") val profileImageUrl: String? = null,
    @SerialName("user_type") val userType: UserType = UserType.STANDARD,
)

@Serializable
enum class UserType(val value: String) {
    @SerialName("admin") ADMIN("admin"),
    @SerialName("standard") STANDARD("standard"),
}

@Serializable
data class AuthUser(
    @SerialName("user") val user: User,
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
)