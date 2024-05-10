package com.strangenaut.attendance.core.domain.repository

import com.strangenaut.attendance.core.domain.model.Credentials
import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.model.Lesson

interface LessonRepository {

    suspend fun getCurrentLesson(): Response

    suspend fun getCurrentLesson(hostEmail: String): Response

    suspend fun setCurrentLesson(lesson: Lesson): Response

    suspend fun setCurrentLesson(lesson: Lesson, parsedCredentials: Credentials): Response

    fun startCurrentLessonListening(
        onDataChange: (lesson: Lesson) -> Unit,
        onCancelled: (errorMessage: String) -> Unit
    )

    fun stopLessonListening()

    suspend fun removeCurrentLesson(): Response

    suspend fun saveHostedLesson(lesson: Lesson): Response

    suspend fun saveAttendedLessonCredentials(lesson: Lesson): Response

    suspend fun getHostedLessons(): Response

    suspend fun getAttendedLessons(): Response

    fun startPastLessonsListening(
        onDataChange: () -> Unit,
        onCancelled: (errorMessage: String) -> Unit
    )
}