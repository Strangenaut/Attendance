package com.strangenaut.attendance.statistics.domain.usecases

import com.strangenaut.attendance.core.domain.repository.LessonRepository

class StartPastLessonsListening(private val repository: LessonRepository) {

    operator fun invoke(
        onDataChange: () -> Unit,
        onCancelled: (errorMessage: String) -> Unit
    ) {
        repository.startPastLessonsListening(
            onDataChange = onDataChange,
            onCancelled = onCancelled
        )
    }
}