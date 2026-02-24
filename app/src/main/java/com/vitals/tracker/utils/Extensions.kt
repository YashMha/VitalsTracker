package com.vitals.tracker.utils

import android.content.Context
import android.graphics.Color
import com.vitals.tracker.R
import com.vitals.tracker.data.model.AlertLevel
import java.text.SimpleDateFormat
import java.util.*

fun Long.toDisplayDateTime(): String =
    SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date(this))

fun Long.toDisplayDate(): String =
    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(this))

fun Long.toDayStart(): Long {
    val cal = Calendar.getInstance().apply { timeInMillis = this@toDayStart
        set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0) }
    return cal.timeInMillis
}

fun Long.toDayEnd(): Long {
    val cal = Calendar.getInstance().apply { timeInMillis = this@toDayEnd
        set(Calendar.HOUR_OF_DAY, 23); set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59); set(Calendar.MILLISECOND, 999) }
    return cal.timeInMillis
}

fun AlertLevel.toColorInt(): Int = when (this) {
    AlertLevel.NORMAL   -> Color.parseColor("#4CAF50")
    AlertLevel.WARNING  -> Color.parseColor("#FF9800")
    AlertLevel.CRITICAL -> Color.parseColor("#F44336")
}

fun AlertLevel.toLabel(): String = name
