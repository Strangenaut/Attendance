package com.strangenaut.attendance.home.presentation.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.strangenaut.attendance.core.navigation.util.setScreen
import com.strangenaut.attendance.home.presentation.HomeState
import com.strangenaut.attendance.home.presentation.host.components.AddDiscipline
import com.strangenaut.attendance.home.presentation.host.components.ChooseDiscipline
import com.strangenaut.attendance.home.presentation.host.components.Host
import com.strangenaut.attendance.home.presentation.host.components.ParticipantsList
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HostNavGraph(
    homeState: StateFlow<HomeState>,
    onAddDiscipline: (discipline: String) -> Unit,
    onRemoveDiscipline: (discipline: String) -> Unit,
    onStartLesson: (discipline: String) -> Unit,
    onStopLesson: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val collectedState = homeState.collectAsState()
    val state by remember {
        mutableStateOf(collectedState)
    }

    val navController = rememberNavController()

    val startDestination = if (state.value.currentLesson == null)
        "choose_discipline"
    else
        "host"

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("choose_discipline") {
            ChooseDiscipline(
                homeState = homeState,
                onChooseDiscipline = {
                    onStartLesson(it)
                    navController.setScreen("host")
                },
                onRemoveDiscipline = onRemoveDiscipline,
                onNavigateToAddDiscipline = {
                    navController.setScreen("add_discipline")
                },
                onNavigateBack = onNavigateBack
            )
        }
        composable("add_discipline") {
            AddDiscipline(
                onAddDiscipline = {
                    onAddDiscipline(it)
                },
                onNavigateBack = {
                    navController.setScreen("choose_discipline")
                }
            )
        }
        composable("host") {
            Host(
                homeState = homeState,
                onNavigateToParticipantsList = {
                    navController.setScreen("participants_list")
                },
                onStopLesson = onStopLesson,
                onNavigateBack = onNavigateBack
            )
        }
        composable("participants_list") {
            ParticipantsList(
                participants = state.value.currentLesson?.participants ?: listOf(),
                onNavigateToAddParticipant = { /*TODO: Implement participants addition*/ },
                onRemoveParticipant = { /*TODO: Implement participants deletion*/ },
                onNavigateBack = {
                    navController.setScreen("host")
                }
            )
        }
    }
}