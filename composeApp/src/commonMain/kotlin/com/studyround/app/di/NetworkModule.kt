package com.studyround.app.di

import com.studyround.app.data.auth.session.SessionManager
import com.studyround.app.data.service.auth.AuthService
import com.studyround.app.data.service.auth.AuthServiceImpl
import com.studyround.app.data.service.survey.RegSurveyService
import com.studyround.app.data.service.survey.RegSurveyServiceImpl
import com.studyround.app.platform.utils.Platform
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {
        HttpClient(engine = get()) {
            // Timeout
            install(HttpTimeout) {
                val timeout = 30_000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }

            // Logging
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        co.touchlab.kermit.Logger.d(messageString = message, tag = "HTTPS Client")
                    }
                }
                level = LogLevel.ALL
            }

            // Content Negotiation
            install(ContentNegotiation) {
                json(get())
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        get<SessionManager>().getAuthCredentials()?.let {
                            BearerTokens(it.authToken, it.refreshToken)
                        }
                    }

                    refreshTokens {
                        get<SessionManager>().refreshAuthCredentials()?.let {
                            BearerTokens(it.authToken, it.refreshToken)
                        }
                    }
                }
            }

            defaultRequest {
                url(get<Platform>().baseApiUrl)
                header("App", "studyround")
            }
        }
    }

    single<Json> {
        Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            isLenient = true
            allowSpecialFloatingPointValues = true
            allowStructuredMapKeys = true
            coerceInputValues = true
        }
    }

    single<AuthService> { AuthServiceImpl(get()) }
    single<RegSurveyService> { RegSurveyServiceImpl(get()) }
}
