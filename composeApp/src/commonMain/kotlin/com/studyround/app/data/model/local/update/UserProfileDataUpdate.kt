package com.studyround.app.data.model.local.update

import com.studyround.app.data.model.local.dto.UserEntity

/**
 * Partial update data class for User profile fetched as part of another object
 */
data class UserProfileDataUpdate(
    val id: Long,
    val email: String,
    val username: String?,
    val profileImageUrl: String?,
) {
    companion object {
        fun from(userEntity: UserEntity): UserProfileDataUpdate {
            return UserProfileDataUpdate(
                id = userEntity.id,
                email = userEntity.email,
                username = userEntity.username,
                profileImageUrl = userEntity.profileImageUrl,
            )
        }
    }
}
