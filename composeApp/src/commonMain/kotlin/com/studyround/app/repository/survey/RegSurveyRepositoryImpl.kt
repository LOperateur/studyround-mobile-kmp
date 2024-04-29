package com.studyround.app.repository.survey

import com.studyround.app.data.remote.dto.User
import com.studyround.app.service.data.resource.Resource
import com.studyround.app.service.data.resource.wrappedResourceFlow
import com.studyround.app.service.survey.RegSurveyService
import kotlinx.coroutines.flow.Flow

class RegSurveyRepositoryImpl(
    private val regSurveyService: RegSurveyService,
) : RegSurveyRepository {
    override fun submitSurvey(
        occupation: String,
        suffix: String,
        source: String,
    ): Flow<Resource<User>> {
        return wrappedResourceFlow {
            regSurveyService.submitSurvey(occupation, suffix, source)
        }
    }
}
