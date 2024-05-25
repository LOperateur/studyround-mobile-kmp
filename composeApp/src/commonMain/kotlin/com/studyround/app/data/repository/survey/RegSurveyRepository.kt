package com.studyround.app.data.repository.survey

import com.studyround.app.data.model.remote.dto.User
import com.studyround.app.data.resource.Resource
import kotlinx.coroutines.flow.Flow

interface RegSurveyRepository {
    fun submitSurvey(
        occupation: String,
        suffix: String,
        source: String,
    ): Flow<Resource<User>>
}
