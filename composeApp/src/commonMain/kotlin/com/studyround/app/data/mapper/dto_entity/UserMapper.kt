package com.studyround.app.data.mapper.dto_entity

import com.studyround.app.data.model.local.dto.UserType
import com.studyround.app.data.model.remote.dto.User

// TODO: Rename DTOs and Entities appropriately
fun User.toEntity(): com.studyround.app.data.model.local.dto.User {
    return com.studyround.app.data.model.local.dto.User(
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
