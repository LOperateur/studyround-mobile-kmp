package com.studyround.app.repository.login

import com.studyround.app.service.data.resource.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl : LoginRepository {
    override fun generateOtp(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            delay(500)
            emit(Resource.Success(Unit))
        }
    }

    override fun validateOtp(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            delay(500)
            emit(Resource.Success(Unit))
        }
    }
}
