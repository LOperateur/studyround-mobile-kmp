package com.studyround.app.data.storage.converters

import androidx.room.TypeConverter
import com.studyround.app.data.model.local.dto.SaleStatus
import com.studyround.app.data.model.local.dto.UserType
import com.studyround.app.utils.DateTimeHelper.dbDateTimeFormat
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format

class StudyRoundConverters {
    @TypeConverter
    fun fromUserType(type: UserType): String {
        return type.value
    }

    @TypeConverter
    fun toUserType(value: String): UserType {
        return UserType.valueOf(value.uppercase())
    }

    @TypeConverter
    fun dateFromTimestamp(value: String): LocalDateTime {
        return LocalDateTime.parse(value, dbDateTimeFormat)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime): String {
        return date.format(dbDateTimeFormat)
    }

    @TypeConverter
    fun fromLongList(value: List<Long>): String {
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun toLongList(value: String): List<Long> {
        if (value.isBlank()) return emptyList()
        return value.split(",").map { it.toLong() }
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(separator = "|")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        if (value.isBlank()) return emptyList()
        return value.split("|").map { it }
    }

    @TypeConverter
    fun fromSaleStatusList(value: List<SaleStatus>): String {
        return value.joinToString(separator = ",") { it.value }
    }

    @TypeConverter
    fun toSaleStatusList(value: String): List<SaleStatus> {
        if (value.isBlank()) return emptyList()
        return value.split(",").map { SaleStatus.valueOf(it.uppercase()) }
    }

    @TypeConverter
    fun fromSaleStatusMap(value: Map<SaleStatus, Boolean>): String {
        return value.entries.joinToString(separator = ",") { "${SaleStatus.valueOf(it.key.value)}|${it.value}" }
    }

    @TypeConverter
    fun toSaleStatusMap(value: String): Map<SaleStatus, Boolean> {
        if (value.isBlank()) return emptyMap()
        return value.split(",").associate {
            val (key, bool) = it.split("|")
            SaleStatus.valueOf(key.uppercase()) to bool.toBoolean()
        }
    }
}
