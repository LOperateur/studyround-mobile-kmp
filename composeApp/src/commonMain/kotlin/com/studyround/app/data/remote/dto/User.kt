package com.studyround.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id: Int,
    @SerialName("email") val email: String,
    @SerialName("username") val username: String? = null,
    @SerialName("firstName") val firstName: String? = null,
    @SerialName("lastName") val lastName: String? = null,
    @SerialName("otherName") val otherName: String? = null,
    @SerialName("about") val about: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("creator") val creator: Boolean = false,
    @SerialName("dateOfBirth")val dateOfBirth: String? = null,
    @SerialName("occupation") val occupation: String? = null,
    @SerialName("proAccount") val proAccount: Boolean = false,
    @SerialName("profileImageUrl") val profileImageUrl: String? = null,
    @SerialName("userType") val userType: UserType = UserType.STANDARD,
)

@Serializable
enum class UserType(val value: String) {
    @SerialName("standard") STANDARD("standard"),
}

@Serializable
data class AuthUser(
    @SerialName("user") val user: User,
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
)