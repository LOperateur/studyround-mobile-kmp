package com.studyround.app.service.login

import com.studyround.app.data.remote.dto.Otp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.http.path

class LoginServiceImpl(
    private val httpClient: HttpClient,
) : LoginService {
    override suspend fun generateOtp(): Otp {
        val response = httpClient.post {
            url {
                path("otp/generate")
            }
        }

        return response.body<Otp>()
    }

    override suspend fun validateOtp() {
        val response = httpClient.post {
            url {
                path("otp/validate")
            }
        }
    }
}
