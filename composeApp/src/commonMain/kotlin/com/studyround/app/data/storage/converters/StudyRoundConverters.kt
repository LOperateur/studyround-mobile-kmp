package com.studyround.app.data.storage.converters

import androidx.room.TypeConverter
import com.studyround.app.data.model.local.dto.UserType

class StudyRoundConverters {
    @TypeConverter
    fun fromUserType(type: UserType): String {
        return type.value
    }

    @TypeConverter
    fun toUserType(value: String): UserType {
        return UserType.valueOf(value.uppercase())
    }
}
