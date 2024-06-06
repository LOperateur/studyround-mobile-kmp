package com.studyround.app.domain.model

data class Category(
    val id: Long,
    val name: String,
    private val courses: List<Course>? = null,
) {
    val coursesInCategory: List<Course> = courses ?: emptyList()
}
