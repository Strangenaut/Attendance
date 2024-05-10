package com.strangenaut.attendance.statistics.presentation.attendances.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.core.domain.model.Participant
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.domain.util.UserPropertiesFormatter.getFancyUserProperties
import com.strangenaut.attendance.core.ui.theme.SurfaceShape

@Composable
fun ParticipantListElementWithAttendanceStatistics(
    disciplineLessons: List<Lesson>,
    user: User,
    modifier: Modifier = Modifier
) {
    val fullName = "${user.surname} ${user.name}"
    val lessonsCount = disciplineLessons.size
    val attendancesCount = disciplineLessons.filter { lesson ->
        var userAttendedLesson = false

        for (participant in lesson.participants) {
            if (participant.user == user) {
                userAttendedLesson = true
                break
            }
        }
        userAttendedLesson
    }.size

    Surface(
        shape = SurfaceShape,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
        ) {
            Row(modifier = Modifier.fillMaxWidth(0.7f)) {
                Icon(
                    painter = painterResource(R.drawable.person),
                    contentDescription = fullName,
                    tint = MaterialTheme.colorScheme.primary
                )
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = fullName,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = user.getFancyUserProperties(),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Justify,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = user.email,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Justify,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$attendancesCount/$lessonsCount",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.displaySmall
                )

                val visitedPercent ="%.1f".format(
                    attendancesCount/lessonsCount.toFloat() * 100
                )

                Text(
                    text = "($visitedPercent%)",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = stringResource(R.string.attended),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Preview
@Composable
private fun ParticipantListElementWithAttendanceStatisticsPreview() {
    val user = User(
        email = "theboywhosurvived@gmail.com",
        name = "Harry",
        surname = "Potter",
        school = "Hogwarts",
        department = "Gryffindor",
        group = ""
    )
    val participant = Participant(
        user = user,
        attendanceTime = ""
    )

    val lessons = listOf(
        Lesson(
            discipline = "Physics",
            date = "12.04.2024",
            time = "16:45",
            participants = listOf(participant)
        ),
        Lesson(
            discipline = "Economics",
            date = "11.04.2024",
            time = "09:00",
        ),
        Lesson(
            discipline = "Computer graphics",
            date = "12.04.2024",
            time = "18:30",
            participants = listOf(participant)
        )
    )

    ParticipantListElementWithAttendanceStatistics(
        user = user,
        disciplineLessons = lessons
    )
}