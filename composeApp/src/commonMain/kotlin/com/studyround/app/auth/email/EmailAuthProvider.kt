package com.studyround.app.auth.email

interface EmailAuthProvider {
    fun signup(
        username: String,
        password: String,
        passToken: String?,
        onAuthResult: () -> Unit,
        onAuthError: (Throwable) -> Unit,
    )

    fun login(
        userIdentity: String,
        password: String,
        onAuthResult: () -> Unit,
        onAuthError: (Throwable) -> Unit,
    )

    fun resetPassword(
        password: String,
        passToken: String?,
        onAuthResult: () -> Unit,
        onAuthError: (Throwable) -> Unit,
    )
}
