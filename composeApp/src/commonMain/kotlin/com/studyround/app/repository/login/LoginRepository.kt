package com.studyround.app.repository.login

interface LoginRepository {
    fun generateOtp()

    fun validateOtp()
}
