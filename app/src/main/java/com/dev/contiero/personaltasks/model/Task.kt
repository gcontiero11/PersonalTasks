package com.dev.contiero.personaltasks.model

import android.os.Parcelable
import com.dev.contiero.personaltasks.model.Constant.INVALID_TASK_ID
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Task(
    val id: Int? = INVALID_TASK_ID,
    var title: String,
    var description: String,
    var dateTime: LocalDateTime,
    var isDone: Boolean
) : Parcelable
