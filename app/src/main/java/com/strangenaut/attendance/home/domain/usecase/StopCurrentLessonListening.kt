package com.strangenaut.attendance.home.domain.usecase

import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.repository.LessonRepository

class StopCurrentLessonListening(private val repository: LessonRepository) {

    operator fun invoke() {
        repository.stopLessonListening()
    }
}