package com.studyround.app.service.login

import com.studyround.app.data.remote.dto.Otp
import com.studyround.app.data.remote.request.AuthType
import com.studyround.app.data.remote.response.StudyRoundResponse

interface LoginService {
    suspend fun generateOtp(email: String, authType: AuthType, resend: Boolean): StudyRoundResponse<Otp>

    suspend fun validateOtp()
}
