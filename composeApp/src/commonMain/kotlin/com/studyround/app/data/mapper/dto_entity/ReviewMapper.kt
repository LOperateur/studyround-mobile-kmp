package com.studyround.app.data.mapper.dto_entity

import com.studyround.app.data.model.local.dto.ReviewEntity
import com.studyround.app.data.model.remote.dto.ReviewDto

fun ReviewDto.toEntity() = ReviewEntity(
    id = id,
    rating = rating,
    review = review,
)
