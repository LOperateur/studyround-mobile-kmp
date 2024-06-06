package com.studyround.app.data.mapper.dto_domain

import com.studyround.app.data.model.remote.dto.ReviewDto
import com.studyround.app.domain.model.Review

fun ReviewDto.toDomain() = Review(
    rating = rating,
    review = review,
)
