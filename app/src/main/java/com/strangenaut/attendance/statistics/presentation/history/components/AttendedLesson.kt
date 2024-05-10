package com.strangenaut.attendance.statistics.presentation.history.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.core.domain.model.Participant
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.presentation.components.LabeledTopBarWithNavBack

@Composable
fun AttendedLesson(
    lesson: Lesson,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            LabeledTopBarWithNavBack(
                label = stringResource(R.string.lesson_attended),
                onNavigateBack = onNavigateBack
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LessonLabelWithIcon(
                label = lesson.discipline,
                isHosted = true
            )
            HostingTime(
                date = lesson.date,
                time = lesson.time
            )
            Text(
                text = stringResource(R.string.teacher),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
            )
            val participant = Participant(
                user = lesson.host,
                attendanceTime = ""
            )
            ParticipantListElement(
                participant = participant,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
            )
        }
    }
}

@Preview
@Composable
private fun AttendedLessonPreview() {
    val user = User(
        email = "theboywhosurvived@gmail.com",
        name = "Harry",
        surname = "Potter",
        school = "Hogwarts",
        department = "Gryffindor",
        group = "9+3/4"
    )
    val participant = Participant(
        user = user,
        attendanceTime = "09:00"
    )

    val lesson = Lesson(
        discipline = "Physics",
        date = "12.04.2024",
        time = "16:45",
        host = User(
            email = "alexandrov_aa@mail.com",
            name = "Alexander",
            surname = "Johnson",
            department = "O7",
            school = "MIT"
        ),
        participants = listOf(participant, participant, participant)
    )

    AttendedLesson(
        lesson = lesson,
        onNavigateBack = {}
    )
}