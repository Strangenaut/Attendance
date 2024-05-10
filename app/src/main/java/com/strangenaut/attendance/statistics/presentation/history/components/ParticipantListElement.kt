package com.strangenaut.attendance.statistics.presentation.history.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.domain.model.Participant
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.domain.util.UserPropertiesFormatter.getFancyUserProperties
import com.strangenaut.attendance.core.ui.theme.SurfaceShape

@Composable
fun ParticipantListElement(
    participant: Participant,
    modifier: Modifier = Modifier
) {
    val user = participant.user
    val fullName = "${user?.surname} ${user?.name}"

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
            Row(modifier = Modifier.fillMaxWidth(0.8f)) {
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
                        text = user?.getFancyUserProperties() ?: "",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Justify,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = user?.email ?: "",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Justify,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Text(
                text = participant.attendanceTime,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
private fun ParticipantListElementPreview() {
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
        attendanceTime = "19:30"
    )

    ParticipantListElement(
        participant = participant
    )
}