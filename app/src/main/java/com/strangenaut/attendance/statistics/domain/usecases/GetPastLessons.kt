package com.strangenaut.attendance.statistics.domain.usecases

import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.repository.LessonRepository

class GetPastLessons(private val repository: LessonRepository) {

    suspend operator fun invoke(): List<Lesson> {
        val lessons = mutableListOf<Lesson>()

        when(val getHostedLessonsResponse = repository.getHostedLessons()) {
            is Response.Success -> lessons += (getHostedLessonsResponse.data as List<*>)
                .filterIsInstance<Lesson>()
            is Response.Failure -> throw Exception(getHostedLessonsResponse.message)
        }

        when(val getAttendedLessonsResponse = repository.getAttendedLessons()) {
            is Response.Success -> lessons += (getAttendedLessonsResponse.data as List<*>)
                .filterIsInstance<Lesson>()
            is Response.Failure -> throw Exception(getAttendedLessonsResponse.message)
        }

        return lessons
    }
}