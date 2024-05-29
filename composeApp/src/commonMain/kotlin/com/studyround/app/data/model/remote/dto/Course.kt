package com.studyround.app.data.model.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    @SerialName("id") val id: Long,
    @SerialName("about") val about: String? = null,
    @SerialName("categories") val categories: List<Category> = listOf(),
    @SerialName("course_status") val courseStatus: CourseStatus? = null,
    @SerialName("creator") val creator: User? = null,
    @SerialName("currency") val currency: String? = null,
    @SerialName("formatted_price") val formattedPrice: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("included_question_years") val includedQuestionYears: List<String> = listOf(),
    @SerialName("instructions") val instructions: Instructions? = null,
    @SerialName("last_publish_date") val lastPublishDate: String? = null,
    @SerialName("num_explanations") val numExplanations: Int? = null,
    @SerialName("num_questions") val numQuestions: Int? = null,
    @SerialName("placeholder_course") val isPlaceholderCourse: Boolean = false,
    @SerialName("price") val price: String? = null,
    @SerialName("private") val isPrivate: Boolean = false,
    @SerialName("publish_status") val publishStatus: PublishStatus? = null,
    @SerialName("purchase_status") val purchaseStatus: Map<SaleStatus, Boolean> = mapOf(),
    @SerialName("rating") val rating: Double? = null,
    @SerialName("review_count") val reviewCount: Int? = null,
    @SerialName("sale_status") val saleStatus: List<SaleStatus> = listOf(),
    @SerialName("test") val isTest: Boolean = false,
    @SerialName("test_expiration") val testExpiration: String? = null,
    @SerialName("test_statistics") val testStatistics: TestStatistics? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("user_review") val userReview: Review? = null,
    @SerialName("version") val version: Int? = null,
)

@Serializable
enum class CourseStatus(val value: String) {
    @SerialName("course_status_active")
    COURSE_STATUS_ACTIVE("course_status_active"),
    @SerialName("course_status_suspended")
    COURSE_STATUS_SUSPENDED("course_status_suspended"),
    @SerialName("course_status_expired")
    COURSE_STATUS_EXPIRED("course_status_expired"),
    @SerialName("course_status_closed")
    COURSE_STATUS_CLOSED("course_status_closed"),
    @SerialName("course_status_deleted")
    COURSE_STATUS_DELETED("course_status_deleted"),
    @SerialName("course_status_dummy")
    COURSE_STATUS_DUMMY("course_status_dummy"),
}

@Serializable
enum class PublishStatus(value: String) {
    @SerialName("publish_status_draft")
    PUBLISH_STATUS_DRAFT("publish_status_draft"),
    @SerialName("publish_status_published")
    PUBLISH_STATUS_PUBLISHED("publish_status_published"),
}

@Serializable
data class Category(
    @SerialName("id")val id: Long,
    @SerialName("name")val name: String,
)

@Serializable
enum class SaleStatus(value: String) {
    @SerialName("sale_status_free")
    SALE_STATUS_FREE("sale_status_free"),
    @SerialName("sale_status_explanations")
    SALE_STATUS_EXPLANATIONS("sale_status_explanations"),
    @SerialName("sale_status_paid")
    SALE_STATUS_PAID("sale_status_paid"),
}

@Serializable
data class Instructions(
    @SerialName("extra_id_title") val extraIdTitle: String? = null,
    @SerialName("graded") val isGraded: Boolean = false,
    @SerialName("max_trials") val maxTrials: Int? = null,
    @SerialName("reveal_answers") val shouldRevealAnswers: Boolean = false,
    @SerialName("session_time") val sessionTime: Int? = null,
    @SerialName("user_limit") val userLimit: Int? = null,
)

@Serializable
data class TestStatistics(
    @SerialName("closeable") val isCloseable: Boolean = false,
    @SerialName("closing_time") val closingTime: String? = null,
    @SerialName("expiration") val expiration: String? = null,
    @SerialName("result_expiration") val resultExpiration: String? = null,
    @SerialName("submissions") val submissions: Int = 0,
    @SerialName("users") val users: Int = 0,
)
