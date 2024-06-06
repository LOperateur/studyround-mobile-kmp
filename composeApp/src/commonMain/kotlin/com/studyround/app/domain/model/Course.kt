package com.studyround.app.domain.model

import kotlinx.datetime.LocalDateTime

data class Course(
    val id: Long,
    val about: String?,
    private val categories: List<Category>? = null,
    val creatorUsername: String,
    val creatorImageUrl: String,
    val currency: String,
    val formattedPrice: String,
    val imageUrl: String?,
    val includedQuestionYears: List<String>,
    val instructions: Instructions?,
    val numExplanations: Int,
    val numQuestions: Int,
    val price: String?,
    val isPrivate: Boolean,
    val purchaseStatus: Map<SaleStatus, Boolean>,
    val rating: Float,
    val reviewCount: Int,
    val saleStatus: List<SaleStatus>,
    val isTest: Boolean,
    val testExpiration: LocalDateTime?,
    val title: String,
    val userReview: Review?,
) {
    val categoriesForCourse = categories ?: emptyList()
}

enum class SaleStatus(val value: String) {
    SALE_STATUS_FREE("sale_status_free"),
    SALE_STATUS_EXPLANATIONS("sale_status_explanations"),
    SALE_STATUS_PAID("sale_status_paid"),
}

data class Instructions(
    val extraIdTitle: String,
    val isGraded: Boolean,
    val maxTrials: Int,
    val shouldRevealAnswers: Boolean,
    val sessionTime: Int,
    val userLimit: Int,
)