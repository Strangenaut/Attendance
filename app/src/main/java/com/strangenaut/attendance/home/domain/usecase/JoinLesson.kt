package com.strangenaut.attendance.home.domain.usecase

import com.strangenaut.attendance.core.domain.model.Credentials
import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.core.domain.model.Participant
import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.domain.repository.LessonRepository
import com.strangenaut.attendance.home.domain.util.GlobalTime
import com.strangenaut.attendance.home.domain.util.convertToStandardTimeFormatString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class JoinLesson(private val repository: LessonRepository) {

    suspend operator fun invoke(user: User, credentials: Credentials) {
        val lesson: Lesson

        when (val getCurrentLessonResponse = repository.getCurrentLesson(credentials.host)) {
            is Response.Success -> lesson = getCurrentLessonResponse.data as Lesson
            is Response.Failure -> throw Exception(getCurrentLessonResponse.message)
        }

        val dateTime = LocalDateTime.now()
        val hours = dateTime.hour.convertToStandardTimeFormatString()
        val minutes = dateTime.minute.convertToStandardTimeFormatString()
        val time = "$hours:$minutes"

        val participant = Participant(
            user = user,
            attendanceTime = time
        )

        lesson.participants.forEach {
            if (participant.user == it.user) {
                return
            }
        }

        val lessonWithUpdatedMembers = lesson.copy(
            participants = lesson.participants + participant
        )

        val setCurrentLessonResponse = repository
            .setCurrentLesson(lessonWithUpdatedMembers, credentials)
        val saveAttendedLessonResponse = repository
            .saveAttendedLessonCredentials(lessonWithUpdatedMembers)

        if (setCurrentLessonResponse is Response.Failure) {
            throw Exception(setCurrentLessonResponse.message)
        } else if (saveAttendedLessonResponse is Response.Failure) {
            throw Exception(saveAttendedLessonResponse.message)
        }
    }
}