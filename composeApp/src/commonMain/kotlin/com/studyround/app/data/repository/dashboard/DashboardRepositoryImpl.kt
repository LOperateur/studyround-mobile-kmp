package com.studyround.app.data.repository.dashboard

import com.studyround.app.data.mapper.dto_domain.toDomain
import com.studyround.app.data.mapper.dto_entity.toEntity
import com.studyround.app.data.mapper.entity_domain.toDomain
import com.studyround.app.data.model.remote.dto.CategoryDto
import com.studyround.app.data.model.remote.dto.User
import com.studyround.app.data.resource.Resource
import com.studyround.app.data.resource.mapListData
import com.studyround.app.data.resource.resourceFlow
import com.studyround.app.data.resource.wrappedResourceFlow
import com.studyround.app.data.service.dashboard.DashboardService
import com.studyround.app.data.storage.dao.CategoryDao
import com.studyround.app.data.storage.dao.CourseDao
import com.studyround.app.domain.model.Category
import com.studyround.app.domain.model.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class DashboardRepositoryImpl(
    private val dashboardService: DashboardService,
    private val categoryDao: CategoryDao,
    private val courseDao: CourseDao,
) : DashboardRepository {

    override fun fetchCategorisedCourses(): Flow<Resource<List<Category>>> {
        val localDataFlow = resourceFlow { categoryDao.getTopCategoriesWithCourses() }
            .map { resource -> resource.mapListData { it.toDomain() } }

        val remoteDataFlow = wrappedResourceFlow { dashboardService.fetchCategorisedCourses() }
            .onEach { if (it is Resource.Success) saveTopCategories(it.data) }
            .map { resource -> resource.mapListData { it.toDomain() } }

        return flow {
            // Fetch locally saved data
            localDataFlow.filter { it !is Resource.Error }.collect { emit(it) }

            // Fetch remote data while updating local
            remoteDataFlow.collect { emit(it) }
        }
    }

    override fun fetchCourses(page: Int): Flow<Resource<List<Course>>> {
        return wrappedResourceFlow {
            dashboardService.fetchCourses(page)
        }.map { resource -> resource.mapListData { it.toDomain() } }
    }

    override fun fetchEnrolledCourses(page: Int): Flow<Resource<List<Course>>> {
        return wrappedResourceFlow {
            dashboardService.fetchEnrolledCourses(page)
        }.map { resource -> resource.mapListData { it.toDomain() } }
    }

    override fun refreshUser(): Flow<Resource<User>> {
        return wrappedResourceFlow {
            dashboardService.refreshUser()
        }
    }

    private suspend fun saveTopCategories(data: List<CategoryDto>) {
        categoryDao.reorderAndUpdateCategories(
            data.mapIndexed { index, category ->
                category.toEntity(index)
            }
        )
    }
}
