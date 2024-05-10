package com.strangenaut.attendance.home.presentation.host.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.presentation.components.IconTextButton
import com.strangenaut.attendance.core.presentation.components.LabeledTopBarWithNavBack
import com.strangenaut.attendance.home.presentation.HomeState
import com.strangenaut.attendance.home.presentation.components.QrCodeIcon
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Host(
    homeState: StateFlow<HomeState>,
    onNavigateToParticipantsList: () -> Unit,
    onStopLesson: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val collectedState = homeState.collectAsState()
    val state by remember {
        mutableStateOf(collectedState)
    }

    Scaffold(
        topBar = {
            LabeledTopBarWithNavBack(
                label = state.value.currentLesson?.discipline ?: "",
                onNavigateBack = onNavigateBack
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val credentials = state.value.currentLesson?.credentials
            val text = if (credentials?.token == "") null else credentials.toString()

            QrCodeIcon(text = text)
            IconTextButton(
                text = stringResource(R.string.participants_list),
                painter = painterResource(R.drawable.list),
                modifier = Modifier.padding(top = 16.dp),
                onClick = onNavigateToParticipantsList
            )
            IconTextButton(
                text = stringResource(R.string.stop_lesson),
                painter = painterResource(R.drawable.stop),
                iconColor = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp),
                onClick = {
                    onStopLesson()
                    onNavigateBack()
                }
            )
        }
    }
}