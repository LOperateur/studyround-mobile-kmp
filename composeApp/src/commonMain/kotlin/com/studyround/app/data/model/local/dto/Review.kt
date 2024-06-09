package com.studyround.app.data.model.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

typealias ReviewEntity = Review

@Entity
data class Review(
    @PrimaryKey val id: Long,
    val rating: Short,
    val review: String?,
)
