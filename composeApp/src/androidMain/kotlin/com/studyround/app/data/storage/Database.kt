package com.studyround.app.data.storage

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.studyround.app.platform.utils.PlatformFileProvider

object Database {
    fun createRoomDatabase(context: Context, provider: PlatformFileProvider): RoomDatabase.Builder<StudyRoundDatabase> {
        val dbFilePath = provider.getDatabasePath(DATABASE_FILE_NAME)
        return Room.databaseBuilder<StudyRoundDatabase>(
            context = context,
            name = dbFilePath,
        )
    }
}
