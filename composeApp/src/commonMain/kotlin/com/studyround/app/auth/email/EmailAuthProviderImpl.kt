package com.studyround.app.auth.email

import com.studyround.app.service.data.resource.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class EmailAuthProviderImpl : EmailAuthProvider {
    override fun signup(
        username: String,
        password: String,
        passToken: String?,
    ): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            delay(500)
            emit(Resource.Success(Unit))
        }
    }

    override fun login(
        userIdentity: String,
        password: String,
    ): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            delay(500)
            emit(Resource.Success(Unit))
        }
    }

    override fun resetPassword(
        password: String,
        passToken: String?,
    ): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            delay(500)
            emit(Resource.Success(Unit))
        }
    }

    override fun refreshToken(
        refreshToken: String,
    ): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            delay(500)
            emit(Resource.Success(Unit))
        }
    }
}
