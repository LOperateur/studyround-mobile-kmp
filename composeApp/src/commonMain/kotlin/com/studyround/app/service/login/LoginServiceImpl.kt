package com.studyround.app.service.login

import com.studyround.app.data.remote.dto.Otp
import com.studyround.app.data.remote.request.AuthType
import com.studyround.app.data.remote.response.StudyRoundResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.post
import io.ktor.http.parameters
import io.ktor.http.path

class LoginServiceImpl(
    private val httpClient: HttpClient,
) : LoginService {
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

    override suspend fun validateOtp() {
        val response = httpClient.post {
            url {
                path("otp/validate")
            }
        }
    }
}
