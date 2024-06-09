package com.studyround.app.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.studyround.app.data.model.local.dto.CategoryEntity
import com.studyround.app.data.model.local.dto.CourseEntity
import com.studyround.app.data.model.local.dto.ReviewEntity
import com.studyround.app.data.model.local.dto.UserEntity
import com.studyround.app.data.model.local.update.CourseListDataUpdate

@Dao
interface CourseDao {
    @Query("SELECT * FROM Course ORDER BY localOrder ASC NULLS LAST, localTimestamp DESC")
    suspend fun getAllCourses(): List<CourseEntity>

    @Query("SELECT * FROM Course WHERE id = :id LIMIT 1")
    suspend fun getSingleCourseById(id: Long): CourseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Query("UPDATE Course SET localOrder = NULL")
    suspend fun resetCourseOrdering()

    @Insert(entity = CourseEntity::class)
    suspend fun updateCourseList(courses: List<CourseListDataUpdate>)

    @Query("SELECT * FROM Category WHERE id IN (:courseIds) ORDER BY localOrder ASC NULLS LAST, localTimestamp DESC")
    suspend fun getCategoriesByIds(courseIds: List<Long>): List<CategoryEntity>

    @Query("SELECT * FROM User WHERE id = :id LIMIT 1")
    suspend fun getCourseCreator(id: Long) : UserEntity?

    @Query("SELECT * FROM Review WHERE id = :userReviewId LIMIT 1")
    suspend fun getUserReview(userReviewId: Long) : ReviewEntity?

    @Transaction
    suspend fun getFullCourse(id: Long): CourseEntity? {
        val course = getSingleCourseById(id)?.also {
            it.categories = getCategoriesByIds(it.categoryIds.orEmpty())
            it.creator = it.creatorId?.let { id -> getCourseCreator(id) }
            it.userReview = it.userReviewId?.let { id -> getUserReview(id) }
        }

        return course
    }
}
