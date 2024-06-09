package com.studyround.app.data.mapper.entity_domain

import com.studyround.app.data.model.local.dto.CourseEntity
import com.studyround.app.domain.model.Course
import com.studyround.app.domain.model.SaleStatus

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
        purchaseStatus = purchaseStatus?.mapKeys { SaleStatus.valueOf(it.key.value.uppercase()) }.orEmpty(),
        rating = rating,
        reviewCount = reviewCount ?: 0,
        saleStatus = saleStatus.map { SaleStatus.valueOf(it.value.uppercase()) },
        isTest = isTest,
        testExpiration = testExpiration,
        title = title,
        userReview = userReview?.toDomain(),
    )
}
