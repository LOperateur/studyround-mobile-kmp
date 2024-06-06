package com.studyround.app.data.mapper.entity_domain

import com.studyround.app.data.model.local.dto.CategoryEntity
import com.studyround.app.domain.model.Category

fun CategoryEntity.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        courses = courses?.map { it.toDomain() },
    )
}
