package com.strangenaut.attendance.home.domain.usecase

import com.strangenaut.attendance.core.domain.model.Credentials
import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.repository.LessonRepository
import com.strangenaut.attendance.home.domain.util.TokenGenerator

class UpdateCredentials(private val repository: LessonRepository) {

    suspend operator fun invoke(currentLesson: Lesson): Lesson {
        val token = TokenGenerator.generateToken()
        val credentials = Credentials(
            host = currentLesson.credentials.host,
            id = currentLesson.credentials.id,
            token = token
        )

        val lesson = currentLesson.copy(
            credentials = credentials
        )

        when (val setCurrentLessonResponse = repository.setCurrentLesson(lesson)) {
            is Response.Success -> Unit
            is Response.Failure -> throw Exception(setCurrentLessonResponse.message)
        }

        return lesson
    }
}