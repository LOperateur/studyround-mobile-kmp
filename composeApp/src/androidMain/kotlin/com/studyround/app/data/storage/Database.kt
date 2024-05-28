package com.studyround.app.data.storage

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

object Database {
    fun createRoomDatabase(context: Context): StudyRoundDatabase {
        val dbFile = context.getDatabasePath(DATABASE_FILE_NAME)
        return Room.databaseBuilder<StudyRoundDatabase>(context, dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}
