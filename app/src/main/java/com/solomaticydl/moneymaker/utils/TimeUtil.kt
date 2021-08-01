package com.solomaticydl.moneymaker.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    fun dateToDateString(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }
        return timestampToDateString(calendar.time.time)
    }

    fun timestampToDateString(timestamp: Long, locale: Locale = Locale.getDefault()): String {
        return SimpleDateFormat("yyyy-MM-dd", locale).format(Date(timestamp))
    }

    fun dateStringToTimestamp(dateStr: String, locale: Locale = Locale.getDefault()): Long {
        val date = try {
            SimpleDateFormat("yyyy-MM-dd", locale).parse(dateStr)
        } catch (e: Exception) {
            return 0
        }
        return date.time
    }
}