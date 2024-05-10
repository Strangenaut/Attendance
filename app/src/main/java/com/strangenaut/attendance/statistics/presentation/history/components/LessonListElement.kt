package com.strangenaut.attendance.statistics.presentation.history.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
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
import com.strangenaut.attendance.core.ui.theme.SurfaceShape

@Composable
fun LessonListElement(
    lesson: Lesson,
    isHosted: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hostUser = lesson.host ?: User()
    val icon = if (isHosted) {
        painterResource(R.drawable.book)
    } else {
        painterResource(R.drawable.checkmark)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Surface(
        shape = SurfaceShape,
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
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
            Row(modifier = Modifier.fillMaxWidth(0.9f)) {
                Icon(
                    painter = icon,
                    contentDescription = lesson.discipline,
                    tint = MaterialTheme.colorScheme.primary
                )
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = lesson.discipline,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    if (isHosted) {
                        Text(
                            text = stringResource(R.string.participants_count)
                                    + " ${lesson.participants.size}",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Justify,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    } else {
                        Text(
                            text = listOf(
                                "${hostUser.surname} ${hostUser.name[0]}.",
                                hostUser.department,
                                hostUser.school
                            ).joinToString(" • "),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Justify,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            Text(
                text = lesson.time,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Preview
@Composable
private fun LessonListElementPreview() {
    val user = User(
        email = "theboywhosurvived@gmail.com",
        name = "Harry",
        surname = "Potter",
        school = "Hogwarts",
        department = "Gryffindor",
        group = "9+3/4"
    )
    val lesson = Lesson(
        discipline = "Linear Algebra",
        time = "12:40",
        host = user,
        participants = listOf(Participant(), Participant())
    )

    LessonListElement(
        lesson = lesson,
        isHosted = false,
        onClick = {}
    )
}