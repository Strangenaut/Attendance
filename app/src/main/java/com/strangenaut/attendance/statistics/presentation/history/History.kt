package com.strangenaut.attendance.statistics.presentation.history

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.core.domain.model.Participant
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.statistics.domain.util.DateFormatter
import com.strangenaut.attendance.statistics.domain.util.LessonsDateTime.sortedByDateTime
import com.strangenaut.attendance.statistics.presentation.components.EmptyHint
import com.strangenaut.attendance.statistics.presentation.history.components.LessonListElement

@Composable
fun History(
    currentUserEmail: String,
    lessons: List<Lesson>,
    onNavigateToHostedLesson: (lesson: Lesson) -> Unit,
    onNavigateToAttendedLesson: (lesson: Lesson) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sortedLessons = lessons.sortedByDateTime()
    val dateHintStyle = MaterialTheme.typography.bodyMedium.copy(
        fontWeight = FontWeight.Light
    )

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (sortedLessons.isEmpty()) {
            item {
                EmptyHint()
            }
            return@LazyColumn
        }

        items(sortedLessons.size) { i ->
            val dateLabel = DateFormatter.getFancyDateString(context, sortedLessons[i].date)
            val isHosted = sortedLessons[i].host?.email == currentUserEmail

            if (i == 0 || sortedLessons[i].date != sortedLessons[i - 1].date) {
                Text(
                    text = dateLabel,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = dateHintStyle,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            LessonListElement(
                lesson = sortedLessons[i],
                isHosted = isHosted,
                onClick = {
                    if (isHosted) {
                        onNavigateToHostedLesson(sortedLessons[i])
                    } else {
                        onNavigateToAttendedLesson(sortedLessons[i])
                    }
                },
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp
                )
            )
        }
    }
}

@Preview
@Composable
private fun HistoryPreview() {
    val currentUserEmail = "denisder2002@mail.ru"
    val lessons = listOf(
        Lesson(
            discipline = "Physics",
            date = "12.04.2024",
            time = "16:45",
            host = User(name = "Alexander", surname = "Johnson", department = "O7", school = "MIT")
        ),
        Lesson(
            discipline = "Economics",
            date = "11.04.2024",
            time = "09:00",
            host = User(name = "Alexander", surname = "Johnson", department = "O7", school = "MIT")
        ),
        Lesson(
            discipline = "Computer graphics",
            date = "12.04.2024",
            time = "18:30",
            host = User(email = currentUserEmail),
            participants = listOf(Participant())
        ),
        Lesson(
            discipline = "Linear Algebra",
            date = "13.04.2024",
            time = "10:50",
            host = User(name = "Alexander", surname = "Johnson", department = "O7", school = "MIT")
        ),
        Lesson(
            discipline = "Parallel computing theory and multithreading",
            date = "11.04.2024",
            time = "10:50",
            host = User(email = currentUserEmail),
            participants = listOf(Participant(), Participant(), Participant())
        ),
        Lesson(
            discipline = "Programming",
            date = "13.04.2024",
            time = "12:40",
            host = User(email = currentUserEmail),
            participants = listOf(Participant(), Participant())
        )
    )

    History(
        currentUserEmail,
        lessons,
        onNavigateToHostedLesson = {},
        onNavigateToAttendedLesson = {}
    )
}