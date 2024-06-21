package com.studyround.app.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.studyround.app.data.model.local.dto.CategoryEntity
import com.studyround.app.data.model.local.dto.CourseEntity
import com.studyround.app.data.model.local.dto.ReviewEntity
import com.studyround.app.data.model.local.dto.UserEntity
import com.studyround.app.data.model.local.update.CourseListDataUpdate
import com.studyround.app.data.model.local.update.UserProfileDataUpdate

@Dao
interface CourseDao {
    @Query("SELECT * FROM Course ORDER BY localOrder ASC NULLS LAST, localTimestamp DESC LIMIT :limit")
    suspend fun getAllCourses(limit: Int): List<CourseEntity>

    @Query("SELECT * FROM Course WHERE id = :id LIMIT 1")
    suspend fun getSingleCourseById(id: Long): CourseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Query("UPDATE Course SET localOrder = NULL")
    suspend fun resetCourseOrdering()

    @Upsert(entity = CourseEntity::class)
    suspend fun updateCourseList(courses: List<CourseListDataUpdate>)

    @Query("SELECT * FROM Category WHERE id IN (:courseIds) ORDER BY localOrder ASC NULLS LAST, localTimestamp DESC")
    suspend fun getCategoriesByIds(courseIds: List<Long>): List<CategoryEntity>

    @Query("SELECT * FROM User WHERE id = :id LIMIT 1")
    suspend fun getCourseCreator(id: Long): UserEntity?

    @Upsert(entity = UserEntity::class)
    suspend fun updateCourseCreator(user: UserProfileDataUpdate)

    @Query("SELECT * FROM Review WHERE id = :userReviewId LIMIT 1")
    suspend fun getUserReview(userReviewId: Long): ReviewEntity?

    @Transaction
    suspend fun getFullCourse(id: Long): CourseEntity? {
        val course = getSingleCourseById(id)?.also {
            it.categories = getCategoriesByIds(it.categoryIds.orEmpty())
            it.creator = it.creatorId?.let { id -> getCourseCreator(id) }
            it.userReview = it.userReviewId?.let { id -> getUserReview(id) }
        }

        return course
    }

    @Transaction
    suspend fun getCoursesWithCreator(limit: Int): List<CourseEntity> {
        val courses = getAllCourses(limit).map {
            it.creator = it.creatorId?.let { id -> getCourseCreator(id) }
            it
        }

        return courses
    }

    @Transaction
    suspend fun updateAndReorderCourseList(courses: List<CourseEntity>) {
        val courseUpdates = courses.mapIndexed { index, course ->
            CourseListDataUpdate.from(course, index)
        }

        resetCourseOrdering()
        updateCourseList(courseUpdates)

        // Also save some basic user profile data alongside
        courses.forEach { course ->
            val creator = course.creator
            creator?.let { updateCourseCreator(UserProfileDataUpdate.from(it)) }
        }
    }
}
