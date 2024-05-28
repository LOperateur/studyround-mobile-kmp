package com.studyround.app.data.auth.model

import com.studyround.app.platform.ui.PlatformContext

sealed interface AuthType

data class EmailAuthType(
    val userIdentity: String,
    val password: String,
    val passToken: String,
) : AuthType

data class GoogleAuthType(val context: PlatformContext) : AuthType
