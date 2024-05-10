package com.strangenaut.attendance.statistics.presentation.history.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
fun HostedLesson(
    lesson: Lesson,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            LabeledTopBarWithNavBack(
                label = stringResource(R.string.lesson_hosted),
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
                text = stringResource(R.string.participants),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
            )
            LazyColumn {
                items(lesson.participants.size) { i ->
                    ParticipantListElement(
                        participant = lesson.participants[i],
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HostedLessonPreview() {
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
        host = User(name = "Alexander", surname = "Alexandrov", department = "O7", school = "MIT"),
        participants = listOf(participant, participant, participant)
    )

    HostedLesson(
        lesson = lesson,
        onNavigateBack = {}
    )
}