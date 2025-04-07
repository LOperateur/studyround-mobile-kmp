package com.studyround.app.data.model.local.dto

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings
import kotlinx.datetime.Instant

typealias CourseEntity = Course

@Suppress(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity
data class Course(
    @PrimaryKey val id: Long,
    val about: String?,
    val categoryIds: List<Long>?,
    val courseStatus: CourseStatus?,
    val creatorId: Long?,
    val currency: String,
    val formattedPrice: String,
    val imageUrl: String?,
    val includedQuestionYears: List<String>?,
    val lastPublishDate: Instant?,
    val numExplanations: Int?,
    val numQuestions: Int?,
    val isPlaceholderCourse: Boolean,
    val price: String?,
    val isPrivate: Boolean?,
    val publishStatus: PublishStatus?,
    val purchaseStatus: Map<SaleStatus, Boolean>?,
    val rating: Float,
    val reviewCount: Int?,
    val saleStatus: List<SaleStatus>,
    val isTest: Boolean,
    val testExpiration: Instant?,
    val title: String,
    val userReviewId: Long?,
    val version: Int,
    val localOrder: Int?,
    val localTimestamp: Instant,
) {
    // Derived fields
    @Ignore var categories: List<CategoryEntity>? = null
    @Ignore var creator: UserEntity? = null
    @Ignore var userReview: Review? = null
}

enum class CourseStatus(val value: String) {
    COURSE_STATUS_ACTIVE("course_status_active"),
    COURSE_STATUS_SUSPENDED("course_status_suspended"),
    COURSE_STATUS_EXPIRED("course_status_expired"),
    COURSE_STATUS_CLOSED("course_status_closed"),
    COURSE_STATUS_DELETED("course_status_deleted"),
    COURSE_STATUS_DUMMY("course_status_dummy"),
}

enum class PublishStatus(val value: String) {
    PUBLISH_STATUS_DRAFT("publish_status_draft"),
    PUBLISH_STATUS_PUBLISHED("publish_status_published"),
}

enum class SaleStatus(val value: String) {
    SALE_STATUS_FREE("sale_status_free"),
    SALE_STATUS_EXPLANATIONS("sale_status_explanations"),
    SALE_STATUS_PAID("sale_status_paid"),
}
