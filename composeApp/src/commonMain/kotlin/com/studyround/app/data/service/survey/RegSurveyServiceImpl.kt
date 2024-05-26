package com.studyround.app.data.service.survey

import com.studyround.app.data.model.remote.dto.User
import com.studyround.app.data.model.remote.response.StudyRoundResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.http.HttpMethod
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
                append("profile_image_url", "x") // To prevent deleting the avatar
            }
        ) {
            method = HttpMethod.Put
            url { path("user") }
        }

        return response.body<StudyRoundResponse<User>>().assertNoErrors
    }
}
