package com.studyround.app.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

object DateTimeHelper {
    val dbDateTimeFormat = LocalDateTime.Format {
        date(LocalDate.Formats.ISO)
        char(' ')
        time(LocalTime.Formats.ISO)
    }

    val serverDateTimeFormat = LocalDateTime.Format {
        date(LocalDate.Formats.ISO)
        char('T')
        time(LocalTime.Formats.ISO)
        char('Z')
    }

    fun getCurrentDateTimeUTC(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.UTC)
    }
}
