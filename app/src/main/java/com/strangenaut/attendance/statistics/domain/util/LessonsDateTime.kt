package com.strangenaut.attendance.statistics.domain.util

import com.strangenaut.attendance.core.domain.model.Lesson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LessonsDateTime {

    private const val TIME_PATTERN_LENGTH = 8
    private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy")
    private fun getLessonDateTimeString(time: String, date: String): String {
        return "$time $date"
    }

    fun List<Lesson>.sortedByDateTime(): List<Lesson> {
        return this.sortedByDescending { lesson ->
            val time = lesson
                .credentials
                .id
                .substringAfter(';')
                .substring(0, TIME_PATTERN_LENGTH)
            val lessonDateTimeString = getLessonDateTimeString(time, lesson.date)
            val lessonDateTime = LocalDateTime.parse(lessonDateTimeString, formatter)

            lessonDateTime
        }
    }
}