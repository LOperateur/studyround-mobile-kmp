package com.studyround.app.data.repository.survey

import com.studyround.app.data.mapper.dto_entity.toEntity
import com.studyround.app.data.model.remote.dto.User
import com.studyround.app.data.resource.Resource
import com.studyround.app.data.resource.wrappedResourceFlow
import com.studyround.app.data.service.survey.RegSurveyService
import com.studyround.app.data.storage.dao.UserDao
import com.studyround.app.data.storage.preferences.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class RegSurveyRepositoryImpl(
    private val regSurveyService: RegSurveyService,
    private val appPreferences: AppPreferences,
    private val userDao: UserDao,
) : RegSurveyRepository {
    override fun submitSurvey(
        occupation: String,
        suffix: String,
        source: String,
    ): Flow<Resource<User>> {
        return wrappedResourceFlow {
            regSurveyService.submitSurvey(occupation, suffix, source)
        }.onEach {
            if (it is Resource.Success) {
                it.data.toEntity().let { user ->
                    appPreferences.saveProfile(user)
                    appPreferences.setShouldDisplaySurveyScreen(false)
                    userDao.insertUser(user)
                }
            }
        }
    }
}
