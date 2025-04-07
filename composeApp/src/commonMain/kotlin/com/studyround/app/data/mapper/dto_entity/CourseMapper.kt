package com.studyround.app.data.mapper.dto_entity

import com.studyround.app.data.model.local.dto.CourseEntity
import com.studyround.app.data.model.local.dto.CourseStatus
import com.studyround.app.data.model.local.dto.PublishStatus
import com.studyround.app.data.model.local.dto.SaleStatus
import com.studyround.app.data.model.remote.dto.CourseDto
import com.studyround.app.utils.DateTimeHelper.dateTimeFormat
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

fun CourseDto.toEntity(order: Int? = null): CourseEntity {
    return CourseEntity(
        id = id,
        about = about,
        categoryIds = categories.map { it.id },
        courseStatus = courseStatus?.name?.let { CourseStatus.valueOf(it) } ?: CourseStatus.COURSE_STATUS_ACTIVE,
        creatorId = creator?.id,
        currency = currency.orEmpty(),
        formattedPrice = formattedPrice.orEmpty(),
        imageUrl = imageUrl,
        includedQuestionYears = includedQuestionYears,
        numExplanations = numExplanations ?: 0,
        numQuestions = numQuestions ?: 0,
        price = price,
        isPlaceholderCourse = isPlaceholderCourse,
        isPrivate = isPrivate,
        lastPublishDate = lastPublishDate?.let { Instant.parse(it, dateTimeFormat) },
        publishStatus = publishStatus?.let { PublishStatus.valueOf(it.name) } ?: PublishStatus.PUBLISH_STATUS_PUBLISHED,
        purchaseStatus = purchaseStatus.mapKeys { SaleStatus.valueOf(it.key.name) },
        rating = rating ?: 0f,
        reviewCount = reviewCount ?: 0,
        saleStatus = saleStatus.map { SaleStatus.valueOf(it.name) },
        isTest = isTest,
        testExpiration = testExpiration?.let { Instant.parse(it, dateTimeFormat) } ?: Clock.System.now(),
        title = title.orEmpty(),
        userReviewId = userReview?.id,
        version = version ?: 0,
        localOrder = order,
        localTimestamp = Clock.System.now(),
    ).also {
        // Map relations to derived fields
        it.categories = categories.map { category -> category.toEntity() }
        it.userReview = userReview?.toEntity()
        it.creator = creator?.toEntity()
    }
}
