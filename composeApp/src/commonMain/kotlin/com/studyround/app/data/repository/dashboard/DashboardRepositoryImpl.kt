package com.studyround.app.data.repository.dashboard

import com.studyround.app.data.model.remote.dto.Category
import com.studyround.app.data.model.remote.dto.Course
import com.studyround.app.data.model.remote.dto.User
import com.studyround.app.data.resource.Resource
import com.studyround.app.data.resource.wrappedResourceFlow
import com.studyround.app.data.service.dashboard.DashboardService
import kotlinx.coroutines.flow.Flow

class DashboardRepositoryImpl(
    private val dashboardService: DashboardService,
) : DashboardRepository {
    override fun fetchCategorisedCourses(): Flow<Resource<List<Category>>> {
        return wrappedResourceFlow {
            dashboardService.fetchCategorisedCourses()
        }
    }

    override fun fetchCourses(page: Int): Flow<Resource<List<Course>>> {
        return wrappedResourceFlow {
            dashboardService.fetchCourses(page)
        }
    }

    override fun fetchEnrolledCourses(page: Int): Flow<Resource<List<Course>>> {
        return wrappedResourceFlow {
            dashboardService.fetchEnrolledCourses(page)
        }
    }

    override fun refreshUser(): Flow<Resource<User>> {
        return wrappedResourceFlow {
            dashboardService.refreshUser()
        }
    }
}
