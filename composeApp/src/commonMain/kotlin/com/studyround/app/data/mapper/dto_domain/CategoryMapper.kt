package com.studyround.app.data.mapper.dto_domain

import com.studyround.app.data.model.remote.dto.CategoryDto
import com.studyround.app.domain.model.Category

fun CategoryDto.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        courses = courses?.map { it.toDomain() },
    )
}
