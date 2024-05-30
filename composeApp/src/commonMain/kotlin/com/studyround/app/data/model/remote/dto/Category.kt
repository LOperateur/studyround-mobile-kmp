package com.studyround.app.data.model.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("id")val id: Long,
    @SerialName("name")val name: String,
    @SerialName("courses") val courses: List<Course>? = null
)
