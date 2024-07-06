package com.studyround.app.data.resource

import com.studyround.app.data.model.remote.response.StudyRoundResponse

data class PageData(
    val page: Int,
    val pageSize: Int,
    val total: Int,
) {
    companion object {
        fun <T> fromStudyRoundResponse(response: StudyRoundResponse<T>): PageData? {
            return with(response) {
                if (page == null || pageSize == null || total == null) {
                    null
                } else {
                    PageData(page = page, pageSize = pageSize, total = total)
                }
            }
        }

        // TODO: Local data will also be paginated in future.
    }
}
