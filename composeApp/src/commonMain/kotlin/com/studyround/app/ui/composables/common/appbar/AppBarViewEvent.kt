package com.studyround.app.ui.composables.common.appbar

sealed interface AppBarViewEvent

data class MenuToggled(val opened: Boolean) : AppBarViewEvent

data class DarkModeToggled(val opened: Boolean) : AppBarViewEvent

data class NavDestinationClicked(val destination: AppBarNavDestination) : AppBarViewEvent
