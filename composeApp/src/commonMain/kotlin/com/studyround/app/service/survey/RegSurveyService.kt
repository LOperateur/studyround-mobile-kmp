package com.studyround.app.service.survey

import com.studyround.app.data.remote.dto.User
import com.studyround.app.data.remote.response.StudyRoundResponse

interface RegSurveyService {

    suspend fun submitSurvey(
        occupation: String,
        suffix: String,
        source: String,
    ): StudyRoundResponse<User>
}
