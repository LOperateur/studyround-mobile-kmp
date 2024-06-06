package com.studyround.app.data.mapper.entity_domain

import com.studyround.app.data.model.local.dto.ReviewEntity
import com.studyround.app.domain.model.Review

fun ReviewEntity.toDomain() = Review(
    rating = rating,
    review = review,
)
