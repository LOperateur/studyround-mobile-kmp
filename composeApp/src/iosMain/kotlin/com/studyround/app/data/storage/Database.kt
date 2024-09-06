package com.studyround.app.data.storage

import androidx.room.Room
import androidx.room.RoomDatabase
import com.studyround.app.platform.utils.PlatformFileProvider

object Database {
    fun createRoomDatabase(provider: PlatformFileProvider): RoomDatabase.Builder<StudyRoundDatabase> {
        val dbFile = provider.getDatabasePath(DATABASE_FILE_NAME)
        return Room.databaseBuilder<StudyRoundDatabase>(
            name = dbFile,
        )
    }
}
