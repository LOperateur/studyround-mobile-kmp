package com.studyround.app.data.mapper.entity_domain

import com.studyround.app.data.model.local.dto.CourseEntity
import com.studyround.app.domain.model.Course
import com.studyround.app.domain.model.SaleStatus
import com.studyround.app.utils.DateTimeHelper.toCurrentLocalDateTime

fun CourseEntity.toDomain(): Course {
    return Course(
        id = id,
        about = about,
        categories = categories?.map { it.toDomain() },
        creatorUsername = creator?.username.orEmpty(),
        creatorImageUrl = creator?.profileImageUrl.orEmpty(),
        currency = currency,
        formattedPrice = formattedPrice,
        imageUrl = imageUrl,
        includedQuestionYears = includedQuestionYears.orEmpty(),
        instructions = null,
        numExplanations = numExplanations ?: 0,
        numQuestions = numQuestions ?: 0,
        price = price,
        isPrivate = isPrivate ?: false,
        purchaseStatus = purchaseStatus?.mapKeys { SaleStatus.valueOf(it.key.name) }.orEmpty(),
        rating = rating,
        reviewCount = reviewCount ?: 0,
        saleStatus = saleStatus.map { SaleStatus.valueOf(it.name) },
        isTest = isTest,
        testExpiration = testExpiration?.toCurrentLocalDateTime(),
        title = title,
        userReview = userReview?.toDomain(),
    )
}
