package com.studyround.app.ui.composables.common.appbar

sealed interface AppBarViewEffect

data class RequestNavigate(val destination: AppBarNavDestination): AppBarViewEffect
