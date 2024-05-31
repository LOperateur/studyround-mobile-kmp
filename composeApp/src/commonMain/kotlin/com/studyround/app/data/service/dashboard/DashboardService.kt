package com.studyround.app.data.service.dashboard

import com.studyround.app.data.model.remote.dto.Category
import com.studyround.app.data.model.remote.dto.Course
import com.studyround.app.data.model.remote.dto.User
import com.studyround.app.data.model.remote.response.StudyRoundResponse

interface DashboardService {
    suspend fun fetchCategorisedCourses(): StudyRoundResponse<List<Category>>

    suspend fun fetchCourses(page: Int): StudyRoundResponse<List<Course>>

    suspend fun fetchEnrolledCourses(page: Int): StudyRoundResponse<List<Course>>

    suspend fun refreshUser(): StudyRoundResponse<User>
}
