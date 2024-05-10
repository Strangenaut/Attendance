package com.strangenaut.attendance.home.presentation.options

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.presentation.components.TopBar
import com.strangenaut.attendance.home.presentation.components.IconTextButtonWithDescription
import com.strangenaut.attendance.home.presentation.components.StatisticsButton
import com.strangenaut.attendance.statistics.presentation.StatisticsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun Options(
    currentUserEmail: String,
    statisticsState: StateFlow<StatisticsState>,
    onNavigateToStatistics: () -> Unit,
    onNavigateToJoinLesson: () -> Unit,
    onNavigateToHostLesson: () -> Unit,
) {
    val collectedState = statisticsState.collectAsState()
    val state by remember {
        mutableStateOf(collectedState)
    }

    val lessonsHosted = state.value.lessons.filter { lesson ->
        lesson.host?.email == currentUserEmail
    }.size
    val lessonsAttended = state.value.lessons.size - lessonsHosted

    Scaffold(
        topBar = {
            TopBar {
                Icon(
                    painter = painterResource(R.drawable.app_icon_small),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(8.dp)
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            StatisticsButton(
                lessonsHosted = lessonsHosted,
                lessonsAttended = lessonsAttended,
                label = stringResource(R.string.statistics),
                attendanceLabel = stringResource(R.string.lessons_attended),
                hostingLabel = stringResource(R.string.lessons_hosted),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                onClick = onNavigateToStatistics
            )
            IconTextButtonWithDescription(
                iconPainter = painterResource(R.drawable.connect),
                title = stringResource(R.string.join),
                description = stringResource(R.string.join_description),
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = onNavigateToJoinLesson
            )
            IconTextButtonWithDescription(
                iconPainter = painterResource(R.drawable.add),
                title = stringResource(R.string.host),
                description = stringResource(R.string.host_description),
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = onNavigateToHostLesson
            )
        }
    }
}

@Preview
@Composable
fun OptionsPreview() {
    Options(
        currentUserEmail = "",
        statisticsState = MutableStateFlow(StatisticsState()),
        onNavigateToStatistics = {},
        onNavigateToJoinLesson = {},
        onNavigateToHostLesson = {}
    )
}