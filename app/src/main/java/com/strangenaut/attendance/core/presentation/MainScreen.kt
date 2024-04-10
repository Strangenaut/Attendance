package com.strangenaut.attendance.core.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.presentation.components.TabBar
import com.strangenaut.attendance.core.presentation.model.TabBarItem
import com.strangenaut.attendance.core.navigation.util.setScreen
import com.strangenaut.attendance.home.presentation.HomeScreen
import com.strangenaut.attendance.home.presentation.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    homeState: StateFlow<HomeState>,
    onAddDiscipline: (discipline: String) -> Unit,
    onStartLesson: (discipline: String) -> Unit,
    onStopLesson: () -> Unit
) {
    val homeTab = TabBarItem(
        title = "Home",
        selectedIcon = painterResource(R.drawable.home_selected),
        unselectedIcon = painterResource(R.drawable.home)
    )
    val statisticsTab = TabBarItem(
        title = "Statistics",
        selectedIcon = painterResource(R.drawable.statistics_selected),
        unselectedIcon = painterResource(R.drawable.statistics),
    )
    val settingsTab = TabBarItem(
        title = "Settings",
        selectedIcon = painterResource(R.drawable.settings_selected),
        unselectedIcon = painterResource(R.drawable.settings)
    )

    val tabBarItems = listOf(homeTab, statisticsTab, settingsTab)
    val navController = rememberNavController()
    val currentTabIndex = MutableStateFlow(0)

    Scaffold(
        bottomBar = {
            TabBar(
                currentTabIndex = currentTabIndex,
                tabBarItems = tabBarItems,
                navController = navController
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = homeTab.title
        ) {
            composable(homeTab.title) {
                HomeScreen(
                    homeState = homeState,
                    onAddDiscipline = onAddDiscipline,
                    onStartLesson = onStartLesson,
                    onStopLesson = onStopLesson,
                    onNavigateToStatistics = {
                        currentTabIndex.update { 1 }
                        navController.setScreen(statisticsTab.title)
                    }
                )
            }
            composable(statisticsTab.title) {

            }
            composable(settingsTab.title) {

            }
        }
    }
}