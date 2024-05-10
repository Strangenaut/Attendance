package com.strangenaut.attendance.home.domain.usecase

import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.repository.LessonRepository

class GetCurrentLesson(private val repository: LessonRepository) {

    suspend operator fun invoke(): Lesson {
        var lesson = Lesson()

        when (val getCurrentLessonResponse = repository.getCurrentLesson()) {
            is Response.Success -> {
                if (getCurrentLessonResponse.data is Lesson) {
                    lesson = getCurrentLessonResponse.data
                }
            }
            is Response.Failure -> throw Exception(getCurrentLessonResponse.message)
        }

        return lesson
    }
}