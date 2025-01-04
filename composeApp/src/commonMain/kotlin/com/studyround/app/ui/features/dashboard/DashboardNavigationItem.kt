package com.studyround.app.ui.features.dashboard

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import studyround.composeapp.generated.resources.*

sealed class DashboardNavigationItem(
    val topLevelRoute: DashboardDestination,
    val titleRes: StringResource,
    val iconRes: DrawableResource,
) {
    data object HomeTab : DashboardNavigationItem(
        topLevelRoute = DashboardDestination.Home,
        titleRes = Res.string.home,
        iconRes = Res.drawable.ic_home,
    )

    data object CoursesTab : DashboardNavigationItem(
        topLevelRoute = DashboardDestination.Courses(),
        titleRes = Res.string.courses,
        iconRes = Res.drawable.ic_menu_book,
    )
}
