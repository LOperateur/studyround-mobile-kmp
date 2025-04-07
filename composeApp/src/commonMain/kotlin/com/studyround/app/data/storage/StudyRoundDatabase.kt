package com.studyround.app.data.storage

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.studyround.app.data.model.local.dto.CategoryEntity
import com.studyround.app.data.model.local.dto.CourseEntity
import com.studyround.app.data.model.local.dto.ReviewEntity
import com.studyround.app.data.model.local.dto.UserEntity
import com.studyround.app.data.storage.converters.StudyRoundConverters
import com.studyround.app.data.storage.dao.CategoryDao
import com.studyround.app.data.storage.dao.CourseDao
import com.studyround.app.data.storage.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal const val DATABASE_FILE_NAME = "studyround.db"

@Database(
    entities = [
        UserEntity::class, CourseEntity::class, CategoryEntity::class, ReviewEntity::class,
    ],
    version = 1
)
@TypeConverters(StudyRoundConverters::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class StudyRoundDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun courseDao(): CourseDao
    abstract fun categoryDao(): CategoryDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<StudyRoundDatabase> {
    override fun initialize(): StudyRoundDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<StudyRoundDatabase>
): StudyRoundDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
