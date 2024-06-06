package com.studyround.app.data.mapper.dto_domain

import com.studyround.app.data.model.remote.dto.CourseDto
import com.studyround.app.data.model.remote.dto.InstructionsDto
import com.studyround.app.domain.model.Course
import com.studyround.app.domain.model.Instructions
import com.studyround.app.domain.model.SaleStatus
import com.studyround.app.utils.DateTimeHelper.getCurrentDateTimeUTC
import com.studyround.app.utils.DateTimeHelper.serverDateTimeFormat
import kotlinx.datetime.LocalDateTime

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
        purchaseStatus = purchaseStatus.mapKeys { SaleStatus.valueOf(it.key.value.uppercase()) },
        rating = rating ?: 0f,
        reviewCount = reviewCount ?: 0,
        saleStatus = saleStatus.map { SaleStatus.valueOf(it.value.uppercase()) },
        isTest = isTest,
        testExpiration = testExpiration?.let { LocalDateTime.parse(it, serverDateTimeFormat) } ?: getCurrentDateTimeUTC(),
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
