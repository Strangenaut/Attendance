package com.strangenaut.attendance.statistics.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.core.navigation.util.setScreen
import com.strangenaut.attendance.statistics.presentation.StatisticsScreen
import com.strangenaut.attendance.statistics.presentation.StatisticsState
import com.strangenaut.attendance.statistics.presentation.attendances.components.Attendances
import com.strangenaut.attendance.statistics.presentation.history.components.AttendedLesson
import com.strangenaut.attendance.statistics.presentation.history.components.HostedLesson
import kotlinx.coroutines.flow.StateFlow

@Composable
fun StatisticsNavGraph(
    statisticsState: StateFlow<StatisticsState>,
    currentUserEmail: String
) {
    val navController = rememberNavController()
    val startDestination = Screen.Statistics.route

    var chosenLesson by remember {
        mutableStateOf(Lesson())
    }

    var chosenDiscipline by remember {
        mutableStateOf("")
    }

    var chosenDisciplineLessons by remember {
        mutableStateOf(listOf<Lesson>())
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Statistics.route) {
            StatisticsScreen(
                statisticsState = statisticsState,
                currentUserEmail = currentUserEmail,
                onNavigateToHostedLesson = { lesson ->
                    chosenLesson = lesson
                    navController.setScreen(Screen.HostedLesson.route)
                },
                onNavigateToAttendedLesson = { lesson ->
                    chosenLesson = lesson
                    navController.setScreen(Screen.AttendedLesson.route)
                },
                onNavigateToAttendances = { discipline, disciplineLessons ->
                    chosenDiscipline = discipline
                    chosenDisciplineLessons = disciplineLessons
                    navController.setScreen(Screen.DisciplineAttendances.route)
                },
            )
        }
        composable(Screen.HostedLesson.route) {
            HostedLesson(
                lesson = chosenLesson,
                onNavigateBack = {
                    navController.setScreen(Screen.Statistics.route)
                }
            )
        }
        composable(Screen.AttendedLesson.route) {
            AttendedLesson(
                lesson = chosenLesson,
                onNavigateBack = {
                    navController.setScreen(Screen.Statistics.route)
                }
            )
        }
        composable(Screen.DisciplineAttendances.route) {
            Attendances(
                discipline = chosenDiscipline,
                disciplineLessons = chosenDisciplineLessons,
                onNavigateBack = {
                    navController.setScreen(Screen.Statistics.route)
                }
            )
        }
    }
}