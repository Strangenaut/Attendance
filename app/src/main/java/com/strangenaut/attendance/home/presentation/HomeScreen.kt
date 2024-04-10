package com.strangenaut.attendance.home.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.strangenaut.attendance.core.navigation.util.setScreen
import com.strangenaut.attendance.home.presentation.host.HostLesson
import com.strangenaut.attendance.home.presentation.join.JoinLesson
import com.strangenaut.attendance.home.presentation.join.components.Options
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    homeState: StateFlow<HomeState>,
    onAddDiscipline: (discipline: String) -> Unit,
    onStartLesson: (discipline: String) -> Unit,
    onStopLesson: () -> Unit,
    onNavigateToStatistics: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "options"
    ) {
        composable("options") {
            Options(
                onNavigateToStatistics = onNavigateToStatistics,
                onNavigateToJoinLesson = {
                    navController.navigate("join_lesson")
                },
                onNavigateToHostLesson = {
                    navController.navigate("host_lesson")
                }
            )
        }
        composable("join_lesson") {
            JoinLesson(
                onNavigateBack = {
                    navController.setScreen("options")
                }
            )
        }
        composable("host_lesson") {
            HostLesson(
                homeState = homeState,
                onAddDiscipline = onAddDiscipline,
                onStartLesson = onStartLesson,
                onStopLesson = onStopLesson,
                onNavigateBack = {
                    navController.setScreen("options")
                }
            )
        }
    }
}