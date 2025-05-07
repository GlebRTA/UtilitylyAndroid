package com.gvituskins.utilityly.presentation.core.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun LocalDate.toMillisAtTime(zoneId: String = "UTC"): Long {
    return this.atStartOfDay(ZoneId.of(zoneId)).toInstant().toEpochMilli()
}

fun Long.timeMillsToLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}
