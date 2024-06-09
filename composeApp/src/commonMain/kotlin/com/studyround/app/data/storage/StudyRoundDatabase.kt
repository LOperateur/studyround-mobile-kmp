package com.studyround.app.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.studyround.app.data.model.local.dto.CategoryEntity
import com.studyround.app.data.model.local.dto.CourseEntity
import com.studyround.app.data.model.local.dto.ReviewEntity
import com.studyround.app.data.model.local.dto.User
import com.studyround.app.data.storage.converters.StudyRoundConverters
import com.studyround.app.data.storage.dao.CategoryDao
import com.studyround.app.data.storage.dao.CourseDao
import com.studyround.app.data.storage.dao.UserDao

internal const val DATABASE_FILE_NAME = "studyround.db"

@Database(
    entities = [
        User::class, CourseEntity::class, CategoryEntity::class, ReviewEntity::class,
    ],
    version = 1
)
@TypeConverters(StudyRoundConverters::class)
abstract class StudyRoundDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun courseDao(): CourseDao
    abstract fun categoryDao(): CategoryDao
}
