package com.studyround.app.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateTimeHelper {
//    val dateTimeFormat = LocalDateTime.Format {
//        date(LocalDate.Formats.ISO)
//        char('T')
//        time(LocalTime.Formats.ISO)
//        char('Z')
//    }

    val dateTimeFormat = DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET

    fun getCurrentLocalDateTime(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun Instant.toCurrentLocalDateTime(): LocalDateTime {
        return this.toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun LocalDateTime.toInstant(): Instant {
        return this.toInstant(TimeZone.currentSystemDefault())
    }
}
