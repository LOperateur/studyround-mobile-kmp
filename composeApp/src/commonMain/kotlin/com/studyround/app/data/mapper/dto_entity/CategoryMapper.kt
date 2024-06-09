package com.studyround.app.data.mapper.dto_entity

import com.studyround.app.data.model.local.dto.CategoryEntity
import com.studyround.app.data.model.remote.dto.CategoryDto
import com.studyround.app.utils.DateTimeHelper

fun CategoryDto.toEntity(order: Int? = null): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        courseIds = courses?.map { it.id },
        localOrder = order,
        localTimestamp = DateTimeHelper.getCurrentDateTimeUTC(),
    ).also {
        // Map relations to derived fields
        it.courses = courses?.map { course -> course.toEntity() }
    }
}
