package com.studyround.app.repository.login

import com.studyround.app.service.data.resource.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun generateOtp(): Flow<Resource<Unit>>

    fun validateOtp(): Flow<Resource<Unit>>
}
