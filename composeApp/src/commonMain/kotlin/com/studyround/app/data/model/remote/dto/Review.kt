package com.studyround.app.data.model.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias ReviewDto = Review

@Serializable
data class Review(
    @SerialName("id") val id: Long,
    @SerialName("rating") val rating: Short,
    @SerialName("review") val review: String? = null,
)
