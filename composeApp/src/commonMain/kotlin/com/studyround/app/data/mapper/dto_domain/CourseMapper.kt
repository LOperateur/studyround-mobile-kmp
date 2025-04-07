package com.studyround.app.data.mapper.dto_domain

import com.studyround.app.data.model.remote.dto.CourseDto
import com.studyround.app.data.model.remote.dto.InstructionsDto
import com.studyround.app.domain.model.Course
import com.studyround.app.domain.model.Instructions
import com.studyround.app.domain.model.SaleStatus
import com.studyround.app.utils.DateTimeHelper.dateTimeFormat
import com.studyround.app.utils.DateTimeHelper.getCurrentLocalDateTime
import com.studyround.app.utils.DateTimeHelper.toCurrentLocalDateTime
import kotlinx.datetime.Instant

fun CourseDto.toDomain(): Course {
    return Course(
        id = id,
        about = about,
        categories = categories.map { it.toDomain() },
        creatorUsername = creator?.username.orEmpty(),
        creatorImageUrl = creator?.profileImageUrl.orEmpty(),
        currency = currency.orEmpty(),
        formattedPrice = formattedPrice.orEmpty(),
        imageUrl = imageUrl,
        includedQuestionYears = includedQuestionYears,
        instructions = instructions?.toDomain(),
        numExplanations = numExplanations ?: 0,
        numQuestions = numQuestions ?: 0,
        price = price.orEmpty(),
        isPrivate = isPrivate,
        purchaseStatus = purchaseStatus.mapKeys { SaleStatus.valueOf(it.key.name) },
        rating = rating ?: 0f,
        reviewCount = reviewCount ?: 0,
        saleStatus = saleStatus.map { SaleStatus.valueOf(it.name) },
        isTest = isTest,
        testExpiration = testExpiration?.let { Instant.parse(it, dateTimeFormat).toCurrentLocalDateTime() } ?: getCurrentLocalDateTime(),
        title = title.orEmpty(),
        userReview = userReview?.toDomain(),
    )
}

private fun InstructionsDto.toDomain(): Instructions {
    return Instructions(
        extraIdTitle = extraIdTitle.orEmpty(),
        isGraded = isGraded,
        maxTrials = maxTrials ?: 0,
        shouldRevealAnswers = shouldRevealAnswers,
        sessionTime = sessionTime ?: 0,
        userLimit = userLimit ?: 0,
    )
}
