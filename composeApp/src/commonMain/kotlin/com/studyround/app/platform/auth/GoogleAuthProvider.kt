package com.studyround.app.platform.auth

import com.studyround.app.platform.ui.PlatformContext

interface GoogleAuthProvider {
    suspend fun login(context: PlatformContext): GoogleAuthResult

    suspend fun logout()
}

data class GoogleAuthResult(
    val token: String,
    val email: String,
    val firstName: String?,
    val lastName: String?,
)
