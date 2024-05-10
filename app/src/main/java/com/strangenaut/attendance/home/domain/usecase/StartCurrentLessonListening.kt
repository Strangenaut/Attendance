package com.strangenaut.attendance.home.domain.usecase

import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.core.domain.repository.LessonRepository

class StartCurrentLessonListening(private val repository: LessonRepository) {

    operator fun invoke(
        onDataChange: (lesson: Lesson) -> Unit,
        onCancelled: (errorMessage: String) -> Unit
    ) {
        repository.startCurrentLessonListening(
            onDataChange = onDataChange,
            onCancelled = onCancelled
        )
    }
}