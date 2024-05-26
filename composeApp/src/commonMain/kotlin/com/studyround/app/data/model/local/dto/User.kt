package com.studyround.app.data.model.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Int,
    val email: String,
    val username: String?,
    val firstName: String?,
    val lastName: String?,
    val otherName: String?,
    val about: String?,
    val country: String?,
    val creator: Boolean,
    val dateOfBirth: String?,
    val occupation: String?,
    val proAccount: Boolean,
    val profileImageUrl: String?,
    val userType: UserType,
)

enum class UserType(val value: String) {
    ADMIN("admin"),
    STANDARD("standard"),
}
