package com.studyround.app.service.auth

import com.studyround.app.data.remote.dto.AccessToken
import com.studyround.app.data.remote.dto.AuthUser
import com.studyround.app.data.remote.dto.Otp
import com.studyround.app.data.remote.dto.PassToken
import com.studyround.app.data.remote.request.AuthType
import com.studyround.app.data.remote.response.StudyRoundResponse

// TODO: Consider renaming to AuthService
interface AuthService {
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
        passToken: String,
    ): StudyRoundResponse<AuthUser>

    suspend fun refreshToken(
        refreshToken: String,
    ): StudyRoundResponse<AccessToken>

    suspend fun generateOtp(email: String, authType: AuthType, resend: Boolean): StudyRoundResponse<Otp>

    suspend fun validateOtp(otpId: Int, otp: String): StudyRoundResponse<PassToken>
}
