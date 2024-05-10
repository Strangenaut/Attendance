package com.strangenaut.attendance.statistics.domain.util

import android.content.Context
import com.strangenaut.attendance.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormatter {

    fun getFancyDateString(context: Context, dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val date = LocalDate.parse(dateString, formatter)
        val monthId = getMonthResourceId(date.monthValue)

        val today = LocalDate.now()
        var dateLabel = context.getString(monthId, date.dayOfMonth)

        if (date == today) {
            dateLabel = "${context.getString(R.string.today)} • $dateLabel"
        }

        if (date == today.minusDays(1)) {
            dateLabel = "${context.getString(R.string.yesterday)} • $dateLabel"
        }

        if (date.year != today.year) {
            dateLabel += " • ${date.year}"
        }

        return dateLabel
    }

    private fun getMonthResourceId(monthNumber: Int): Int {
        return when (monthNumber) {
            1 -> R.string.january
            2 -> R.string.february
            3 -> R.string.march
            4 -> R.string.april
            5 -> R.string.may
            6 -> R.string.june
            7 -> R.string.july
            8 -> R.string.august
            9 -> R.string.september
            10 -> R.string.october
            11 -> R.string.november
            12 -> R.string.december
            else -> throw IllegalArgumentException("Invalid month number")
        }
    }
}