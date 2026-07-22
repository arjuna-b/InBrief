package com.arjuna.inbrief.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun timeAgoFromISO(ISOTime: String): String{
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val past = LocalDateTime.parse(ISOTime,formatter)
            val now = LocalDateTime.now()

            val duration = java.time.Duration.between(past,now)

            val days = duration.toDays()
            val hours = duration.toHours() % 24
            val minutes = duration.toMinutes() % 60

            return when {
                days > 0 -> "$days day ${if (days > 1) "s" else ""}  $hours hour${if(hours > 1) "s" else ""} $minutes min${if(minutes>1) "s" else ""} ago"
                hours > 0 -> "$hours hour${if (hours>1) "s" else ""} ago"
                minutes > 0 -> "$minutes ago"
                else -> "Just now"
            }
}

@RequiresApi(Build.VERSION_CODES.O)
fun toDate(ISO:String): LocalDateTime {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    return LocalDateTime.parse(ISO,formatter)
}