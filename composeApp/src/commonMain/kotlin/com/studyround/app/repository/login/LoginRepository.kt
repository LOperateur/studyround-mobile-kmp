package com.studyround.app.repository.login

import com.studyround.app.data.remote.dto.Otp
import com.studyround.app.data.remote.request.AuthType
import com.studyround.app.platform.ui.PlatformContext
import com.studyround.app.service.data.resource.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun generateOtp(email: String, authType: AuthType, resend: Boolean): Flow<Resource<Otp>>

    fun validateOtp(): Flow<Resource<Unit>>
}
