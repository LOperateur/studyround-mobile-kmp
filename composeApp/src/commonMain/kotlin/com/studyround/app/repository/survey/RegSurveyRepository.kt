package com.studyround.app.repository.survey

import com.studyround.app.data.remote.dto.User
import com.studyround.app.service.data.resource.Resource
import kotlinx.coroutines.flow.Flow

interface RegSurveyRepository {
    fun submitSurvey(
        occupation: String,
        suffix: String,
        source: String,
    ): Flow<Resource<User>>
}
