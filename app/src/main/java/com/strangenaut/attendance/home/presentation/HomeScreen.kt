package com.strangenaut.attendance.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.strangenaut.attendance.core.navigation.util.setScreen
import com.strangenaut.attendance.core.domain.model.Credentials
import com.strangenaut.attendance.home.presentation.host.HostNavGraph
import com.strangenaut.attendance.home.presentation.join.Join
import com.strangenaut.attendance.home.presentation.options.Options
import com.strangenaut.attendance.statistics.presentation.StatisticsState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    homeState: StateFlow<HomeState>,
    statisticsState: StateFlow<StatisticsState>,
    onAddDiscipline: (discipline: String) -> Unit,
    onRemoveDiscipline: (discipline: String) -> Unit,
    onStartLesson: (discipline: String) -> Unit,
    onStopLesson: () -> Unit,
    onJoinLesson: (credentials: Credentials) -> Unit,
    onNavigateToStatistics: () -> Unit
) {
    val collectedState = homeState.collectAsState()
    val state by remember {
        mutableStateOf(collectedState)
    }

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "options"
    ) {
        composable("options") {
            Options(
                currentUserEmail = state.value.currentUser.email,
                statisticsState = statisticsState,
                onNavigateToStatistics = onNavigateToStatistics,
                onNavigateToJoinLesson = {
                    navController.setScreen("join_lesson")
                },
                onNavigateToHostLesson = {
                    navController.setScreen("host_lesson")
                }
            )
        }
        composable("join_lesson") {
            Join(
                onJoinLesson = onJoinLesson,
                onNavigateBack = {
                    navController.setScreen("options")
                }
            )
        }
        composable("host_lesson") {
            HostNavGraph(
                homeState = homeState,
                onAddDiscipline = onAddDiscipline,
                onRemoveDiscipline = onRemoveDiscipline,
                onStartLesson = onStartLesson,
                onStopLesson = onStopLesson,
                onNavigateBack = {
                    navController.setScreen("options")
                }
            )
        }
    }
}