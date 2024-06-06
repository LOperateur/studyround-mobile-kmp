package com.studyround.app.data.model.local.dto

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

typealias CategoryEntity = Category

@Entity
data class Category(
    @PrimaryKey val id: Long,
    val name: String,
    val courseIds: List<Long>?,
    val localOrder: Int?,
    val localTimestamp: LocalDateTime,
) {
    // Derived fields
    @Ignore var courses: List<CourseEntity>? = null
}
