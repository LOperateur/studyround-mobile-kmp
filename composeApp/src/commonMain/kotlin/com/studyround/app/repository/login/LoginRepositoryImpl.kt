package com.studyround.app.repository.login

import com.studyround.app.data.remote.dto.Otp
import com.studyround.app.data.remote.request.AuthType
import com.studyround.app.platform.auth.GoogleAuthProvider
import com.studyround.app.platform.ui.PlatformContext
import com.studyround.app.service.data.resource.Resource
import com.studyround.app.service.data.resource.resourceFlow
import com.studyround.app.service.data.resource.wrappedResourceFlow
import com.studyround.app.service.login.LoginService
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class LoginRepositoryImpl(
    private val loginService: LoginService,
    private val googleAuthProvider: GoogleAuthProvider,
) : LoginRepository {

    override fun launchGoogleOauth(
        isSignup: Boolean,
        platformContext: PlatformContext,
    ): Flow<Resource<String>> {
        return resourceFlow {
            withContext(Dispatchers.IO) {
                val result = CompletableDeferred<String>()
                googleAuthProvider.login(
                    context = platformContext,
                    onAuthResult = { result.complete(it.token) },
                    onAuthError = { result.completeExceptionally(it) },
                )
                result.await()
            }
        }
    }

    override fun generateOtp(
        email: String,
        authType: AuthType,
        resend: Boolean,
    ): Flow<Resource<Otp>> {
        return wrappedResourceFlow {
            loginService.generateOtp(email, authType, resend)
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
