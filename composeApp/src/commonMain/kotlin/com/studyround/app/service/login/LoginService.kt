package com.studyround.app.service.login

import com.studyround.app.data.remote.dto.Otp

interface LoginService {
    suspend fun generateOtp(): Otp

    suspend fun validateOtp()
}
