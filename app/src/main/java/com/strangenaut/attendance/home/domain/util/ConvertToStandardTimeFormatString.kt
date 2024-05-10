package com.strangenaut.attendance.home.domain.util

fun Int.convertToStandardTimeFormatString(): String {
    return "${if (this < 10) "0" else ""}$this"
}