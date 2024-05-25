package com.studyround.app.data.repository.auth

import com.studyround.app.data.model.remote.dto.Otp
import com.studyround.app.data.model.remote.dto.PassToken
import com.studyround.app.data.model.remote.request.AuthType
import com.studyround.app.data.resource.Resource
import com.studyround.app.data.resource.wrappedResourceFlow
import com.studyround.app.data.service.auth.AuthService
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val authService: AuthService,
) : AuthRepository {

    override fun generateOtp(
        email: String,
        authType: AuthType,
        resend: Boolean,
    ): Flow<Resource<Otp>> {
        return wrappedResourceFlow {
            authService.generateOtp(email, authType, resend)
        }
    }

    override fun validateOtp(otpId: Int, otp: String): Flow<Resource<PassToken>> {
        return wrappedResourceFlow {
            authService.validateOtp(otpId, otp)
        }
    }
}
