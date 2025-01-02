package com.studyround.app.ui.main

sealed interface RootViewEffect

data class Navigate<T : RootDestination>(val destination: T, val replace: Boolean) : RootViewEffect
