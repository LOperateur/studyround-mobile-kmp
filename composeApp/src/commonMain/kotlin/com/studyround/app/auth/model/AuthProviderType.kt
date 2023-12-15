package com.studyround.app.auth.model

import com.studyround.app.platform.ui.PlatformContext

sealed interface AuthProviderType

data class EmailAuthProviderType(val userIdentity: String, val password: String) : AuthProviderType

data class GoogleAuthProviderType(val context: PlatformContext) : AuthProviderType
