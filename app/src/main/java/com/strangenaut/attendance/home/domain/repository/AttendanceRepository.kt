package com.strangenaut.attendance.home.domain.repository

import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.home.model.Lesson

interface AttendanceRepository {

    suspend fun getUser(email: String): Response

    suspend fun getDisciplines(email: String): Response

    suspend fun getCurrentLesson(email: String): Response

    suspend fun addDiscipline(user: User, discipline: String): Response

    suspend fun setCurrentLesson(lesson: Lesson): Response

    suspend fun removeCurrentLesson(email: String): Response

    suspend fun saveHostedLesson(lesson: Lesson): Response

    suspend fun saveAttendedLesson(lesson: Lesson): Response
}