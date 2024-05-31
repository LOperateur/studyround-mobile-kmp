package com.studyround.app.data.storage

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.studyround.app.platform.utils.PlatformFileProvider
import kotlinx.coroutines.Dispatchers

object Database {
    fun createRoomDatabase(context: Context, provider: PlatformFileProvider): StudyRoundDatabase {
        val dbFilePath = provider.getDatabasePath(DATABASE_FILE_NAME)
        return Room.databaseBuilder<StudyRoundDatabase>(context, dbFilePath)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}
