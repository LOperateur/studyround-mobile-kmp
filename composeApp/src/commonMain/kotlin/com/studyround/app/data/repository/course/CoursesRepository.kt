package com.studyround.app.data.repository.course

import com.studyround.app.data.resource.Resource
import com.studyround.app.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    fun fetchCourseDetails(courseId: Long, refresh: Boolean = false): Flow<Resource<Course>>
}
