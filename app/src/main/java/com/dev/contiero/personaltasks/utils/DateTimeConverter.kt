package com.dev.contiero.personaltasks.utils

import java.time.format.DateTimeFormatter
import java.util.Locale

class DateTimeConverter {
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.getDefault())
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
}