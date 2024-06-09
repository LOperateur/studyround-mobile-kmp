package com.studyround.app.data.mapper.dto_entity

import com.studyround.app.data.model.local.dto.UserEntity
import com.studyround.app.data.model.local.dto.UserType
import com.studyround.app.data.model.remote.dto.UserDto

fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        otherName = otherName,
        about = about,
        country = country,
        creator = creator,
        dateOfBirth = dateOfBirth,
        occupation = occupation,
        proAccount = proAccount,
        profileImageUrl = profileImageUrl,
        userType = UserType.valueOf(userType.value.uppercase()),
    )
}
