package com.strangenaut.attendance.statistics.presentation.attendances

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.core.domain.model.Participant
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.presentation.components.MenuIconItem
import com.strangenaut.attendance.statistics.presentation.components.EmptyHint

@Composable
fun Disciplines(
    currentUserEmail: String,
    lessons: List<Lesson>,
    onNavigateToAttendances: (
        discipline: String,
        disciplineLessons: List<Lesson>
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    val hostedLessons = lessons.filter {  lesson ->
        lesson.host?.email == currentUserEmail
    }.reversed()

    val disciplinesLessons = hashMapOf<String, List<Lesson>>()

    for (lesson in hostedLessons) {
        val discipline = lesson.discipline

        disciplinesLessons[discipline] =
            (disciplinesLessons[discipline] ?: listOf()) + lesson
    }


    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (disciplinesLessons.isEmpty()) {
            item {
                EmptyHint()
            }
            return@LazyColumn
        }
        
        items(disciplinesLessons.keys.size) { i ->
            val discipline = disciplinesLessons.keys.elementAt(i)

            MenuIconItem(
                label = discipline,
                icon = painterResource(R.drawable.book),
                showArrow = true,
                onClick = {
                    onNavigateToAttendances(
                        discipline,
                        disciplinesLessons[discipline] ?: listOf()
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun SubjectsPreview() {
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

    Disciplines(
        currentUserEmail = currentUserEmail,
        lessons = lessons,
        onNavigateToAttendances = { _, _ -> }
    )
}