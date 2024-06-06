package com.studyround.app.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.studyround.app.data.model.local.dto.CourseEntity
import com.studyround.app.data.model.local.update.CourseListDataUpdate

@Dao
interface CourseDao {
    // TODO: NULLS LAST
    @Query("SELECT * FROM Course ORDER BY localOrder ASC, localTimestamp DESC")
    suspend fun getAllCourses(): List<CourseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Query("UPDATE Course SET localOrder = NULL")
    suspend fun resetCourseOrdering()

    @Insert(entity = CourseEntity::class)
    suspend fun updateCourseList(courses: List<CourseListDataUpdate>)

    @Transaction
    suspend fun getCourseWithRelations(): List<CourseEntity> {
        // TODO: Fetch and Update Relations
        val courses = getAllCourses().map {
            it
        }
        return courses
    }
}
