package com.studyround.app.data.storage

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.studyround.app.data.storage.instantiateImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

object Database {
    fun createRoomDatabase(): StudyRoundDatabase {
        val dbFile = "${documentsDirectory()}/$DATABASE_FILE_NAME"
        return Room.databaseBuilder<StudyRoundDatabase>(
            name = dbFile,
            factory = { StudyRoundDatabase::class.instantiateImpl() as StudyRoundDatabase } // TODO: Watch for future fixes
        ).setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    private fun documentsDirectory(): String {
        return NSSearchPathForDirectoriesInDomains(
            directory = NSDocumentDirectory,
            domainMask = NSUserDomainMask,
            true,
        ).first() as String
    }
}
