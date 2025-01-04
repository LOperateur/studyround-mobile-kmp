package com.studyround.app.ui.features.dashboard

import com.studyround.app.ui.navigation.Destination
import kotlinx.serialization.Serializable

sealed class DashboardDestination : Destination {
    @Serializable
    data object Home : DashboardDestination()

    @Serializable
    data class Courses(
        val selectedCategoryId: Long? = null,
        val selectedCourseId: Long? = null,
    ) : DashboardDestination()
}
