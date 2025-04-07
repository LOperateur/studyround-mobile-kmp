package com.studyround.app.data.storage.converters

import androidx.room.TypeConverter
import com.studyround.app.data.model.local.dto.SaleStatus
import com.studyround.app.utils.DateTimeHelper.dateTimeFormat
import kotlinx.datetime.Instant
import kotlinx.datetime.format

class StudyRoundConverters {
    @TypeConverter
    fun dateFromTimestamp(value: String): Instant {
        return Instant.parse(value, dateTimeFormat)
    }

    @TypeConverter
    fun dateToTimestamp(date: Instant): String {
        return date.format(dateTimeFormat)
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
        return value.joinToString(separator = ",") { it.name }
    }

    @TypeConverter
    fun toSaleStatusList(value: String): List<SaleStatus> {
        if (value.isBlank()) return emptyList()
        return value.split(",").map { SaleStatus.valueOf(it) }
    }

    @TypeConverter
    fun fromSaleStatusMap(value: Map<SaleStatus, Boolean>): String {
        return value.entries.joinToString(separator = ",") { "${SaleStatus.valueOf(it.key.name)}|${it.value}" }
    }

    @TypeConverter
    fun toSaleStatusMap(value: String): Map<SaleStatus, Boolean> {
        if (value.isBlank()) return emptyMap()
        return value.split(",").associate {
            val (name, bool) = it.split("|")
            SaleStatus.valueOf(name) to bool.toBoolean()
        }
    }
}
