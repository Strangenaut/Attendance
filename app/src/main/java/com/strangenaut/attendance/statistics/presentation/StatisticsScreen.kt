package com.strangenaut.attendance.statistics.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.statistics.navigation.TabScreen
import com.strangenaut.attendance.statistics.presentation.attendances.Disciplines
import com.strangenaut.attendance.statistics.presentation.components.TextTabBar
import com.strangenaut.attendance.statistics.presentation.history.History
import com.strangenaut.attendance.statistics.presentation.model.TextTabBarItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun StatisticsScreen(
    statisticsState: StateFlow<StatisticsState>,
    currentUserEmail: String,
    onNavigateToHostedLesson: (lesson: Lesson) -> Unit,
    onNavigateToAttendedLesson: (lesson: Lesson) -> Unit,
    onNavigateToAttendances: (
        discipline: String,
        disciplineLessons: List<Lesson>
    ) -> Unit
) {
    val collectedState = statisticsState.collectAsState()
    val state by remember {
        mutableStateOf(collectedState)
    }

    val historyTab = TextTabBarItem(
        title = stringResource(R.string.history),
        route = TabScreen.History.route
    )
    val attendancesTab = TextTabBarItem(
        title = stringResource(R.string.attendances),
        route = TabScreen.Attendances.route
    )

    val tabBarItems = listOf(historyTab, attendancesTab)
    val navController = rememberNavController()
    val currentTabIndex = MutableStateFlow(0)

    Scaffold(
        topBar = {
            TextTabBar(
                currentTabIndex = currentTabIndex,
                textTabBarItems = tabBarItems,
                navController = navController
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = historyTab.route
        ) {
            composable(historyTab.route) {
                History(
                    currentUserEmail = currentUserEmail,
                    lessons = state.value.lessons,
                    onNavigateToHostedLesson = onNavigateToHostedLesson,
                    onNavigateToAttendedLesson = onNavigateToAttendedLesson,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
            composable(attendancesTab.route) {
                Disciplines(
                    currentUserEmail = currentUserEmail,
                    lessons = state.value.lessons,
                    onNavigateToAttendances = onNavigateToAttendances,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
        }
    }
}