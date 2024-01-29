package com.studyround.app.auth.model

sealed interface AuthType

data class EmailAuthType(val userIdentity: String, val password: String) : AuthType

data class GoogleAuthType(val idToken: String) : AuthType
