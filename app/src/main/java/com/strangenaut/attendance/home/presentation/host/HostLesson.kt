package com.strangenaut.attendance.home.presentation.host

import android.annotation.SuppressLint
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
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("RestrictedApi")
@Composable
fun HostLesson(
    homeState: StateFlow<HomeState>,
    onAddDiscipline: (discipline: String) -> Unit,
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
                onNavigateToAddDiscipline = {
                    navController.navigate("add_discipline")
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
                    navController.popBackStack()
                }
            )
        }
        composable("host") {
            Host(
                homeState = homeState,
                onStopLesson = onStopLesson,
                onNavigateBack = onNavigateBack
            )
        }
    }
}