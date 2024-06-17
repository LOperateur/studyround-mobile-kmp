package com.studyround.app.data.model.local.update

import com.studyround.app.data.model.local.dto.CourseEntity
import com.studyround.app.data.model.local.dto.SaleStatus
import com.studyround.app.utils.DateTimeHelper
import kotlinx.datetime.LocalDateTime

/**
 * Partial update data class for courses fetched in a list
 */
data class CourseListDataUpdate(
    val id: Long,
    val title: String,
    val rating: Float,
    val imageUrl: String?,
    val creatorId: Long?,
    val currency: String,
    val formattedPrice: String,
    val isPlaceholderCourse: Boolean,
    val isTest: Boolean,
    val version: Int,
    val saleStatus: List<SaleStatus>,
    val localOrder: Int?,
    val localTimestamp: LocalDateTime,
) {
    companion object {
        fun from(courseEntity: CourseEntity, order: Int): CourseListDataUpdate {
            return CourseListDataUpdate(
                id = courseEntity.id,
                title = courseEntity.title,
                rating = courseEntity.rating,
                imageUrl = courseEntity.imageUrl,
                creatorId = courseEntity.creatorId,
                currency = courseEntity.currency,
                formattedPrice = courseEntity.formattedPrice,
                isPlaceholderCourse = courseEntity.isPlaceholderCourse,
                isTest = courseEntity.isTest,
                version = courseEntity.version,
                saleStatus = courseEntity.saleStatus,
                localOrder = order,
                localTimestamp = DateTimeHelper.getCurrentDateTimeUTC(),
            )
        }
    }
}
