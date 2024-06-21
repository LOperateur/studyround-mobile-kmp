package com.studyround.app.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.studyround.app.data.model.local.dto.CategoryEntity
import com.studyround.app.data.model.local.dto.CourseEntity
import com.studyround.app.data.model.local.update.CategorisedCourseListDataUpdate

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category ORDER BY localOrder ASC NULLS LAST LIMIT 5")
    suspend fun getTopCategories(): List<CategoryEntity>

    @Query("SELECT * FROM Course WHERE id IN (:courseIds) ORDER BY localOrder ASC NULLS LAST, localTimestamp DESC")
    suspend fun getCoursesByIds(courseIds: List<Long>): List<CourseEntity>

    @Query("UPDATE Category SET localOrder = NULL")
    suspend fun resetCategoryOrdering()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCategories(categories: List<CategoryEntity>)

    @Upsert(entity = CourseEntity::class)
    suspend fun updateCategorisedCourses(courses: List<CategorisedCourseListDataUpdate>)

    @Transaction
    suspend fun getTopCategoriesWithCourses(): List<CategoryEntity> {
        val categoriesWithCourses = getTopCategories().map {
            it.courses = getCoursesByIds(it.courseIds.orEmpty())
            it
        }

        return categoriesWithCourses
    }

    @Transaction
    suspend fun updateAndReorderCategories(categories: List<CategoryEntity>) {
        resetCategoryOrdering()
        updateCategories(categories)

        // Also save some basic course data alongside
        categories.forEach { category ->
            updateCategorisedCourses(
                category.courses?.map { CategorisedCourseListDataUpdate.from(it) }.orEmpty()
            )
        }
    }
}
