package com.studyround.app.platform.utils

interface NetworkHelper {
    fun registerListener(onNetworkAvailable: () -> Unit, onNetworkLost: () -> Unit)
    fun unregisterListener()
}
