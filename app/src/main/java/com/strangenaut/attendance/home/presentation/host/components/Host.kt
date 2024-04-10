package com.strangenaut.attendance.home.presentation.host.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.components.IconTextButton
import com.strangenaut.attendance.core.components.LabeledTopBar
import com.strangenaut.attendance.home.presentation.HomeState
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Host(
    homeState: StateFlow<HomeState>,
    onStopLesson: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val collectedState = homeState.collectAsState()
    val state by remember {
        mutableStateOf(collectedState)
    }

    Scaffold(
        topBar = {
            LabeledTopBar(
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
            QrCodeIcon(text = state.value.currentLesson?.credentials.toString())
            IconTextButton(
                text = "Список участников",
                painter = painterResource(R.drawable.list),
                modifier = Modifier.padding(top = 16.dp),
                onClick = {}
            )
            IconTextButton(
                text = "Остановить занятие",
                painter = painterResource(R.drawable.stop),
                modifier = Modifier.padding(top = 8.dp),
                onClick = {
                    onStopLesson()
                    onNavigateBack()
                }
            )
        }
    }
}