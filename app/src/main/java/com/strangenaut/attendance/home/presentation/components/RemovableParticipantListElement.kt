package com.strangenaut.attendance.home.presentation.components

import android.content.res.Resources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.ui.theme.SurfaceShape
import com.strangenaut.attendance.core.domain.model.Participant

// TODO Refactor literals from here
@Composable
fun RemovableParticipantListElement(
    participant: Participant,
    modifier: Modifier = Modifier,
    onRemoveParticipant: (participantEmail: Participant) -> Unit,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    var containerSize by remember {
        mutableStateOf(0.dp)
    }
    val user = participant.user ?: User()

    Surface(
        shape = SurfaceShape,
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .onSizeChanged {
                    val screenDensity = Resources.getSystem().displayMetrics.density
                    containerSize = (it.height / screenDensity).dp
                }
        ) {
            Row(modifier = Modifier.fillMaxWidth(0.8f)) {
                Icon(
                    painter = painterResource(R.drawable.person),
                    contentDescription = "${user.name} ${user.surname}",
                    tint = MaterialTheme.colorScheme.primary
                )
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = "${user.name} ${user.surname}",
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = listOf(
                            user.group,
                            user.department,
                            user.school
                        ).joinToString(" • "),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Justify
                    )
                    Text(
                        text = user.email,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Justify
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(containerSize)
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            onRemoveParticipant(participant)
                        }
                    )
                )
                Text(
                    text = participant.attendanceTime,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

@Preview
@Composable
private fun RemovableParticipantsListElementPreview() {
    val user = User(
        email = "theboywhosurvived@gmail.com",
        name = "Harry",
        surname = "Potter",
        school = "Hogwarts",
        department = "Gryffindor",
        group = "9+3/4"
    )
    val participant = Participant(user, "10:50")
    
    RemovableParticipantListElement(
        participant = participant,
        onRemoveParticipant = {}
    )
}