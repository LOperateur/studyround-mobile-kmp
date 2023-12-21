package com.studyround.app.ui.main

sealed interface RootViewEffect

data class Navigate(val destination: RootDestination) : RootViewEffect
