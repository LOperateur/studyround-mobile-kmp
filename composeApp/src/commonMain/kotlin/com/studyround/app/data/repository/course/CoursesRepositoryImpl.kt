package com.studyround.app.data.repository.course

import com.studyround.app.data.error.AppError
import com.studyround.app.data.error.StudyRoundException
import com.studyround.app.data.mapper.entity_domain.toDomain
import com.studyround.app.data.resource.Resource
import com.studyround.app.data.resource.mapData
import com.studyround.app.data.resource.resourceFlow
import com.studyround.app.data.storage.dao.CourseDao
import com.studyround.app.domain.model.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CoursesRepositoryImpl(
    private val courseDao: CourseDao,
) : CoursesRepository {

    override fun fetchCourseDetails(courseId: Long, refresh: Boolean): Flow<Resource<Course>> {
        val noDataError = StudyRoundException(AppError.EMPTY_RESPONSE_DATA)

        val localDataFlow = resourceFlow { courseDao.getSingleCourseById(courseId) ?: throw noDataError }
            .map { resource -> resource.mapData { it.toDomain() } }

        // val remoteDataFlow = wrappedResourceFlow { coursesService.fetchSingleCourse() }
        //     .onEach { if (it is Resource.Success) saveCourse(it.data) }
        //     .map { resource -> resource.mapData { it.toDomain() } }

        return flow {
            // Fetch locally saved data for non-refresh requests
            if (!refresh) { localDataFlow.filterNot { it is Resource.Error }.collect { emit(it) } }

            // Fetch remote data while updating local
            // remoteDataFlow.collect { emit(it) }
        }
    }
}