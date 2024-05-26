package com.studyround.app.data.service.auth

import com.studyround.app.data.model.remote.dto.AccessToken
import com.studyround.app.data.model.remote.dto.AuthUser
import com.studyround.app.data.model.remote.dto.Otp
import com.studyround.app.data.model.remote.dto.PassToken
import com.studyround.app.data.model.remote.request.AuthType
import com.studyround.app.data.model.remote.request.OtpRequest
import com.studyround.app.data.model.remote.response.StudyRoundResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.plugin
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.http.path

class AuthServiceImpl(
    private val httpClient: HttpClient,
) : AuthService {
    override suspend fun signup(
        username: String,
        password: String,
        passToken: String,
    ): StudyRoundResponse<AuthUser> {
        val response = httpClient.submitForm(
            formParameters = parameters {
                append("username", username)
                append("password", password)
                append("password_confirmation", password)
                append("pass_token", passToken)
            }
        ) {
            url { path("auth/signup") }
        }

        clearToken()
        return response.body<StudyRoundResponse<AuthUser>>().assertNoErrors
    }

    override suspend fun login(
        userIdentity: String,
        password: String,
    ): StudyRoundResponse<AuthUser> {
        val response = httpClient.submitForm(
            formParameters = parameters {
                append("user_identity", userIdentity)
                append("password", password)
            }
        ) {
            url { path("auth/login") }
        }

        clearToken()
        return response.body<StudyRoundResponse<AuthUser>>().assertNoErrors
    }

    override suspend fun googleOauth(idToken: String): StudyRoundResponse<AuthUser> {
        val response = httpClient.submitForm(
            formParameters = parameters {
                append("id_token", idToken)
            }
        ) {
            url { path("auth/google/mobile") }
        }

        clearToken()
        return response.body<StudyRoundResponse<AuthUser>>().assertNoErrors
    }

    override suspend fun resetPassword(
        password: String,
        passToken: String,
    ): StudyRoundResponse<AuthUser> {
        val response = httpClient.submitForm(
            formParameters = parameters {
                append("password", password)
                append("password_confirmation", password)
                append("pass_token", passToken)
            }
        ) {
            url { path("auth/reset") }
        }

        clearToken()
        return response.body<StudyRoundResponse<AuthUser>>().assertNoErrors
    }

    override suspend fun refreshToken(refreshToken: String): StudyRoundResponse<AccessToken> {
        val response = httpClient.submitForm(
            formParameters = parameters {
                append("refresh_token", refreshToken)
            }
        ) {
            url { path("auth/refresh-token") }
        }

        clearToken()
        return response.body<StudyRoundResponse<AccessToken>>().assertNoErrors
    }

    override suspend fun generateOtp(
        email: String,
        authType: AuthType,
        resend: Boolean,
    ): StudyRoundResponse<Otp> {
        val response = httpClient.post {
            contentType(ContentType.Application.Json)
            setBody(
                OtpRequest(
                    userIdentity = email,
                    authType = authType,
                    resend = resend,
                )
            )
            url { path("otp/generate") }
        }

        return response.body<StudyRoundResponse<Otp>>().assertNoErrors
    }

    override suspend fun validateOtp(otpId: Int, otp: String): StudyRoundResponse<PassToken> {
        val response = httpClient.submitForm(
            formParameters = parameters {
                append("otp_id", otpId.toString())
                append("otp", otp)
            }
        ) {
            url {
                path("otp/validate")
            }
        }

        return response.body<StudyRoundResponse<PassToken>>().assertNoErrors
    }

    /**
     * Clears the currently cached token in order to prevent the next request from using it.
     * This forces the next request to call `loadTokens` before executing.
     */
    private fun clearToken() {
        httpClient.plugin(Auth).providers.filterIsInstance<BearerAuthProvider>().first()
            .clearToken()
    }
}