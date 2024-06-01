package com.studyround.app.data.repository.dashboard

import com.studyround.app.data.model.remote.dto.Category
import com.studyround.app.data.model.remote.dto.Course
import com.studyround.app.data.model.remote.dto.User
import com.studyround.app.data.resource.Resource
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun fetchCategorisedCourses(): Flow<Resource<List<Category>>>

    fun fetchCourses(page: Int): Flow<Resource<List<Course>>>

    fun fetchEnrolledCourses(page: Int): Flow<Resource<List<Course>>>

    fun refreshUser(): Flow<Resource<User>>
}
