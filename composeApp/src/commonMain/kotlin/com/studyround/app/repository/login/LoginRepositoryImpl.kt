package com.studyround.app.repository.login

import com.studyround.app.data.remote.dto.Otp
import com.studyround.app.data.remote.request.AuthType
import com.studyround.app.service.data.resource.Resource
import com.studyround.app.service.data.resource.wrappedResourceFlow
import com.studyround.app.service.login.LoginService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(
    private val loginService: LoginService,
) : LoginRepository {

    override fun generateOtp(
        email: String,
        authType: AuthType,
        resend: Boolean,
    ): Flow<Resource<Otp>> {
        return wrappedResourceFlow {
            loginService.generateOtp(email, authType, resend)
        }
    }

    override fun validateOtp(otpId: Int, otp: String): Flow<Resource<Unit>> {
        return wrappedResourceFlow {
            loginService.validateOtp(otpId, otp)
        }
    }
}
