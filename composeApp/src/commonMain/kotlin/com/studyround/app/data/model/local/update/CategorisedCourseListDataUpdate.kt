package com.studyround.app.data.model.local.update

import com.studyround.app.data.model.local.dto.CourseEntity
import com.studyround.app.data.model.local.dto.SaleStatus
import com.studyround.app.utils.DateTimeHelper
import kotlinx.datetime.LocalDateTime

/**
 * Partial update data class for Courses fetched within Categories in a list
 */
data class CategorisedCourseListDataUpdate(
    val id: Long,
    val title: String,
    val rating: Float,
    val imageUrl: String?,
    val currency: String,
    val formattedPrice: String,
    val isPlaceholderCourse: Boolean,
    val isTest: Boolean,
    val version: Int,
    val saleStatus: List<SaleStatus>,
    val localTimestamp: LocalDateTime,
) {
    companion object {
        fun from(courseEntity: CourseEntity): CategorisedCourseListDataUpdate {
            return CategorisedCourseListDataUpdate(
                id = courseEntity.id,
                title = courseEntity.title,
                rating = courseEntity.rating,
                imageUrl = courseEntity.imageUrl,
                currency = courseEntity.currency,
                formattedPrice = courseEntity.formattedPrice,
                isPlaceholderCourse = courseEntity.isPlaceholderCourse,
                isTest = courseEntity.isTest,
                version = courseEntity.version,
                saleStatus = courseEntity.saleStatus,
                localTimestamp = DateTimeHelper.getCurrentDateTimeUTC(),
            )
        }
    }
}
