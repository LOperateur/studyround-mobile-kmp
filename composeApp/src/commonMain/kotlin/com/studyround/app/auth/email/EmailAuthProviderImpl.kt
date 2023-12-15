package com.studyround.app.auth.email

internal class EmailAuthProviderImpl: EmailAuthProvider {
    override fun signup(
        username: String,
        password: String,
        passToken: String?,
        onAuthResult: () -> Unit,
        onAuthError: (Throwable) -> Unit
    ) {

    }

    override fun login(
        userIdentity: String,
        password: String,
        onAuthResult: () -> Unit,
        onAuthError: (Throwable) -> Unit
    ) {

    }

    override fun resetPassword(
        password: String,
        passToken: String?,
        onAuthResult: () -> Unit,
        onAuthError: (Throwable) -> Unit
    ) {

    }
}
