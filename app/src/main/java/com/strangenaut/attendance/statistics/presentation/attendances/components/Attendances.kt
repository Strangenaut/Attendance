package com.strangenaut.attendance.statistics.presentation.attendances.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.presentation.components.LabeledTopBarWithNavBack
import com.strangenaut.attendance.statistics.presentation.components.EmptyHint

@Composable
fun Attendances(
    discipline: String,
    disciplineLessons: List<Lesson>,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val users = mutableSetOf<User>()

    for (lesson in disciplineLessons) {
        for (participant in lesson.participants) {
            val user = participant.user

            if (!users.contains(user) && user != null) {
                users.add(user)
            }
        }
    }

    Scaffold(
        topBar = {
            LabeledTopBarWithNavBack(
                label = discipline,
                onNavigateBack = onNavigateBack
            )
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (users.isEmpty()) {
                item {
                    EmptyHint()
                }
                return@LazyColumn
            }

            items(users.size) { i ->
                ParticipantListElementWithAttendanceStatistics(
                    disciplineLessons = disciplineLessons,
                    user = users.elementAt(i),
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp
                    )
                )
            }
        }
    }
}