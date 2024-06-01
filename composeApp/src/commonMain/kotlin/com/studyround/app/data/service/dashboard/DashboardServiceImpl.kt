package com.studyround.app.data.service.dashboard

import com.studyround.app.data.model.remote.dto.Category
import com.studyround.app.data.model.remote.dto.Course
import com.studyround.app.data.model.remote.dto.User
import com.studyround.app.data.model.remote.response.StudyRoundResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path

class DashboardServiceImpl(
    private val httpClient: HttpClient,
) : DashboardService {
    override suspend fun fetchCategorisedCourses(): StudyRoundResponse<List<Category>> {
        val response = httpClient.get {
            url { path("courses/categorised") }
        }

        return response.body<StudyRoundResponse<List<Category>>>().assertNoErrors
    }

    override suspend fun fetchCourses(page: Int): StudyRoundResponse<List<Course>> {
        val response = httpClient.get {
            url {
                parameter("page", page)
                path("courses")
            }
        }

        return response.body<StudyRoundResponse<List<Course>>>().assertNoErrors
    }

    override suspend fun fetchEnrolledCourses(page: Int): StudyRoundResponse<List<Course>> {
        val response = httpClient.get {
            parameter("page", page)
            url { path("courses") }
        }

        return response.body<StudyRoundResponse<List<Course>>>().assertNoErrors
    }

    override suspend fun refreshUser(): StudyRoundResponse<User> {
        val response = httpClient.get {
            url { path("user") }
        }

        return response.body<StudyRoundResponse<User>>().assertNoErrors
    }
}
