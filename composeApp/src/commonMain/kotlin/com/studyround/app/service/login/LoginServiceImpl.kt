package com.studyround.app.service.login

import com.studyround.app.data.remote.dto.AccessToken
import com.studyround.app.data.remote.dto.AuthUser
import com.studyround.app.data.remote.dto.Otp
import com.studyround.app.data.remote.request.AuthType
import com.studyround.app.data.remote.response.StudyRoundResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.http.parameters
import io.ktor.http.path

class LoginServiceImpl(
    private val httpClient: HttpClient,
) : LoginService {
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

        return response.body<StudyRoundResponse<AccessToken>>().assertNoErrors
    }

    override suspend fun generateOtp(
        email: String,
        authType: AuthType,
        resend: Boolean,
    ): StudyRoundResponse<Otp> {
        val response = httpClient.submitForm(
            formParameters = parameters {
                append("user_identity", email)
                append("type", authType.value)
                append("resend", resend.toString())
            }
        ) {
            url { path("otp/generate") }
        }

        return response.body<StudyRoundResponse<Otp>>().assertNoErrors
    }

    override suspend fun validateOtp(otpId: Int, otp: String): StudyRoundResponse<Unit> {
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

        return response.body<StudyRoundResponse<Unit>>().assertNoErrors
    }
}
