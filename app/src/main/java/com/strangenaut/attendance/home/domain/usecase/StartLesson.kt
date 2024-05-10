package com.strangenaut.attendance.home.domain.usecase

import com.strangenaut.attendance.core.domain.model.Credentials
import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.domain.repository.LessonRepository
import com.strangenaut.attendance.home.domain.util.GlobalTime
import com.strangenaut.attendance.home.domain.util.TokenGenerator
import com.strangenaut.attendance.home.domain.util.convertToStandardTimeFormatString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class StartLesson(private val repository: LessonRepository) {

    suspend operator fun invoke(
        currentUser: User,
        discipline: String
    ): Lesson {
        val dateTime = LocalDateTime.now()

        val day = dateTime.dayOfMonth.convertToStandardTimeFormatString()
        val month = dateTime.monthValue.convertToStandardTimeFormatString()
        val year = dateTime.year
        val date = "${day}.${month}.${year}"

        val hours = dateTime.hour.convertToStandardTimeFormatString()
        val minutes = dateTime.minute.convertToStandardTimeFormatString()
        val seconds = dateTime.second.convertToStandardTimeFormatString()
        val nano = dateTime.nano
        val time = "$hours:$minutes"

        val host = currentUser.email.replace('.', '_')
        val id = "$date;$hours:$minutes:$seconds:$nano".replace('.', '_')
        val initialToken = TokenGenerator.generateToken()

        val credentials = Credentials(
            host = host,
            id = id,
            token = initialToken
        )

        val lesson = Lesson(
            discipline = discipline,
            date = date,
            time = time,
            credentials = credentials,
            host = currentUser,
        )

        when (val setCurrentLessonResponse = repository.setCurrentLesson(lesson)) {
            is Response.Success -> Unit
            is Response.Failure -> throw Exception(setCurrentLessonResponse.message)
        }

        return lesson
    }
}