package com.studyround.app.service.survey

import com.studyround.app.data.remote.dto.User
import com.studyround.app.data.remote.response.StudyRoundResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.http.parameters
import io.ktor.http.path

class RegSurveyServiceImpl(
    private val httpClient: HttpClient,
) : RegSurveyService {
    override suspend fun submitSurvey(
        occupation: String,
        suffix: String,
        source: String, // Todo: Record source differently later
    ): StudyRoundResponse<User> {
        val response = httpClient.submitForm(
            formParameters = parameters {
                append("occupation", "$occupation-$suffix")
            }
        ) {
            url { path("user") }
        }

        return response.body<StudyRoundResponse<User>>().assertNoErrors
    }
}
