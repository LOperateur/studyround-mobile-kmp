package com.studyround.app.data.repository.auth

import com.studyround.app.data.model.remote.dto.Otp
import com.studyround.app.data.model.remote.dto.PassToken
import com.studyround.app.data.model.remote.request.AuthType
import com.studyround.app.data.resource.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun generateOtp(email: String, authType: AuthType, resend: Boolean): Flow<Resource<Otp>>

    fun validateOtp(otpId: Int, otp: String): Flow<Resource<PassToken>>
}
