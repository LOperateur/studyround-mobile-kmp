package com.studyround.app.service.login

import com.studyround.app.data.remote.dto.AuthUser
import com.studyround.app.data.remote.dto.Otp
import com.studyround.app.data.remote.request.AuthType
import com.studyround.app.data.remote.response.StudyRoundResponse

interface LoginService {
    suspend fun signup(
        username: String,
        password: String,
        passToken: String,
    ): StudyRoundResponse<AuthUser>

    suspend fun login(
        userIdentity: String,
        password: String,
    ): StudyRoundResponse<AuthUser>

    suspend fun googleOauth(idToken: String): StudyRoundResponse<AuthUser>

    suspend fun resetPassword(
        password: String,
        passToken: String?,
    ): StudyRoundResponse<AuthUser>

    suspend fun refreshToken(
        refreshToken: String,
    ): StudyRoundResponse<String>

    suspend fun generateOtp(email: String, authType: AuthType, resend: Boolean): StudyRoundResponse<Otp>

    suspend fun validateOtp()
}
