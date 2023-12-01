package com.studyround.app.platform.auth

interface GoogleAuthProvider {
    suspend fun login(onAuthResult: (GoogleAuthResult) -> Unit, onAuthError: (Throwable) -> Unit)
    suspend fun logout()
}

data class GoogleAuthResult(
    val email: String,
    val firstName: String?,
    val lastName: String?,
    val avatarUrl: String?,
)
