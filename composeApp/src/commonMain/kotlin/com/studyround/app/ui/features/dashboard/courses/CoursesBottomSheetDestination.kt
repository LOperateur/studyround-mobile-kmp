package com.studyround.app.ui.features.dashboard.courses

import com.studyround.app.ui.navigation.Destination
import kotlinx.serialization.Serializable

sealed class CoursesBottomSheetDestination : Destination {
    @Serializable
    data class CourseDetailsSheet(val courseId: Long) : CoursesBottomSheetDestination()
}
