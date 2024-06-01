package com.studyround.app.data.storage

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.studyround.app.data.storage.instantiateImpl
import com.studyround.app.platform.utils.PlatformFileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

object Database {
    fun createRoomDatabase(provider: PlatformFileProvider): StudyRoundDatabase {
        val dbFile = provider.getDatabasePath(DATABASE_FILE_NAME)
        return Room.databaseBuilder<StudyRoundDatabase>(
            name = dbFile,
            factory = { StudyRoundDatabase::class.instantiateImpl() as StudyRoundDatabase } // TODO: Watch for future fixes
        ).setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}
