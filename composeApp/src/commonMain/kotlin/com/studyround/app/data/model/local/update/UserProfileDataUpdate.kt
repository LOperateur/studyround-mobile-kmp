package com.studyround.app.data.model.local.update

import com.studyround.app.data.model.local.dto.UserEntity
import com.studyround.app.data.model.local.dto.UserType

/**
 * Partial update data class for User profile fetched as part of another object
 */
data class UserProfileDataUpdate(
    val id: Long,
    val email: String,
    val username: String?,
    val profileImageUrl: String?,
    val creator: Boolean,
    val proAccount: Boolean,
    val userType: UserType,
) {
    companion object {
        fun from(userEntity: UserEntity): UserProfileDataUpdate {
            return UserProfileDataUpdate(
                id = userEntity.id,
                email = userEntity.email,
                username = userEntity.username,
                profileImageUrl = userEntity.profileImageUrl,
                creator = userEntity.creator,
                proAccount = userEntity.proAccount,
                userType = userEntity.userType,
            )
        }
    }
}
