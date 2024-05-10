package com.strangenaut.attendance.home.domain.usecase

import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.repository.LessonRepository

class StopLesson(private val repository: LessonRepository) {

    suspend operator fun invoke(currentLesson: Lesson) {
        when (val removeLessonResponse = repository.removeCurrentLesson()) {
            is Response.Success -> Unit
            is Response.Failure -> throw Exception(removeLessonResponse.message)
        }

        when (val saveHostedLessonResponse = repository.saveHostedLesson(currentLesson)) {
            is Response.Success -> Unit
            is Response.Failure -> throw Exception(saveHostedLessonResponse.message)
        }
    }
}