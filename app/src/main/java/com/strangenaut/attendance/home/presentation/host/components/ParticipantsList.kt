package com.strangenaut.attendance.home.presentation.host.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.presentation.components.AddButton
import com.strangenaut.attendance.core.presentation.components.LabeledTopBarWithNavBackAndIconButton
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.domain.model.Participant
import com.strangenaut.attendance.home.presentation.components.RemovableParticipantListElement
import com.strangenaut.attendance.home.presentation.host.util.copyToClipBoard

@Composable
fun ParticipantsList(
    participants: List<Participant>,
    onNavigateToAddParticipant: () -> Unit,
    onRemoveParticipant: (participant: Participant) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val label = stringResource(R.string.participants_list)

    Scaffold(
        topBar = {
            LabeledTopBarWithNavBackAndIconButton(
                label = label,
                iconPainter = painterResource(R.drawable.copy),
                onClick = {
                    val data = participants.joinToString {
                        it.toString() + '\n'
                    }

                    data.copyToClipBoard(context, label)
                },
                onNavigateBack = onNavigateBack
            )
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            item {
                AddButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp
                        ),
                    onClick = onNavigateToAddParticipant
                )
            }

            items(participants.size) { i ->
                RemovableParticipantListElement(
                    participant = participants[i],
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onRemoveParticipant = onRemoveParticipant
                )
            }
        }
    }
}

@Preview
@Composable
private fun ParticipantsListPreview() {
    val user = User(
        email = "theboywhosurvived@gmail.com",
        name = "Harry",
        surname = "Potter",
        school = "Hogwarts",
        department = "Gryffindor",
        group = "9+3/4"
    )
    val attendance = Participant(user, "10:50")

    val participants = listOf(attendance, attendance, attendance)

    ParticipantsList(
        participants = participants,
        onNavigateToAddParticipant = {},
        onRemoveParticipant = {},
        onNavigateBack = {}
    )
}