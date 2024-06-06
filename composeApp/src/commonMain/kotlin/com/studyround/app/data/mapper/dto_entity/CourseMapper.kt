package com.studyround.app.data.mapper.dto_entity

import com.studyround.app.data.model.local.dto.CourseEntity
import com.studyround.app.data.model.local.dto.CourseStatus
import com.studyround.app.data.model.local.dto.PublishStatus
import com.studyround.app.data.model.local.dto.SaleStatus
import com.studyround.app.data.model.remote.dto.CourseDto
import com.studyround.app.utils.DateTimeHelper
import kotlinx.datetime.LocalDateTime

fun CourseDto.toEntity(order: Int? = null): CourseEntity {
    return CourseEntity(
        id = id,
        about = about,
        categoryIds = categories.map { it.id },
        courseStatus = courseStatus?.value?.uppercase()?.let { CourseStatus.valueOf(it) } ?: CourseStatus.COURSE_STATUS_ACTIVE,
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
        lastPublishDate = lastPublishDate?.let { LocalDateTime.parse(it, DateTimeHelper.serverDateTimeFormat) },
        publishStatus = publishStatus?.let { PublishStatus.valueOf(it.value.uppercase()) } ?: PublishStatus.PUBLISH_STATUS_PUBLISHED,
        purchaseStatus = purchaseStatus.mapKeys { SaleStatus.valueOf(it.key.value.uppercase()) },
        rating = rating ?: 0f,
        reviewCount = reviewCount ?: 0,
        saleStatus = saleStatus.map { SaleStatus.valueOf(it.value.uppercase()) },
        isTest = isTest,
        testExpiration = testExpiration?.let { LocalDateTime.parse(it, DateTimeHelper.serverDateTimeFormat) } ?: DateTimeHelper.getCurrentDateTimeUTC(),
        title = title.orEmpty(),
        userReviewId = userReview?.id,
        version = version ?: 0,
        localOrder = order,
        localTimestamp = DateTimeHelper.getCurrentDateTimeUTC(),
    ).also {
        // Map relations to derived fields
        it.categories = categories.map { category -> category.toEntity() }
        it.userReview = userReview?.toEntity()
        it.creator = creator?.toEntity()
    }
}
