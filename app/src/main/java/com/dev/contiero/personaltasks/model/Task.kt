package com.dev.contiero.personaltasks.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Task(
    val id: Int,
    var title: String,
    var description: String,
    var dateTime: LocalDateTime,
    var done: Boolean
) :Parcelable
