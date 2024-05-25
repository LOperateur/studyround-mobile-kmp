package com.studyround.app.data.service.survey

import com.studyround.app.data.model.remote.dto.User
import com.studyround.app.data.model.remote.response.StudyRoundResponse

interface RegSurveyService {

    suspend fun submitSurvey(
        occupation: String,
        suffix: String,
        source: String,
    ): StudyRoundResponse<User>
}
