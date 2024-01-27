package com.studyround.app.di

import com.studyround.app.platform.utils.Platform
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
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
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
            }

            // ContentNegotiation
            install(ContentNegotiation) {
                json(get())
            }

            defaultRequest {
                url(get<Platform>().baseApiUrl + "/")
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
        }
    }
}
