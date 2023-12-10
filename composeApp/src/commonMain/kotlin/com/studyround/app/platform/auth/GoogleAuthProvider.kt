package com.studyround.app.platform.auth

import com.studyround.app.platform.ui.PlatformContext

interface GoogleAuthProvider {
    suspend fun login(
        context: PlatformContext,
        onAuthResult: (GoogleAuthResult) -> Unit,
        onAuthError: (Throwable) -> Unit,
    )

    suspend fun logout()
}

data class GoogleAuthResult(
    val token: String,
    val email: String,
    val firstName: String?,
    val lastName: String?,
)
