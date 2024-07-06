package com.studyround.app.data.repository.dashboard

import com.studyround.app.data.model.remote.dto.User
import com.studyround.app.data.resource.Resource
import com.studyround.app.domain.model.Category
import com.studyround.app.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun fetchCategorisedCourses(refresh: Boolean = false): Flow<Resource<List<Category>>>

    fun fetchCourses(
        page: Int,
        limit: Int = 12,
        refresh: Boolean = false,
    ): Flow<Resource<List<Course>>>

    fun fetchEnrolledCourses(page: Int): Flow<Resource<List<Course>>>

    fun refreshUser(): Flow<Resource<User>>
}
