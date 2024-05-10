package com.strangenaut.attendance.core.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.presentation.components.IconTabBar
import com.strangenaut.attendance.core.presentation.model.IconTabBarItem
import com.strangenaut.attendance.core.navigation.util.setScreen
import com.strangenaut.attendance.core.domain.model.Credentials
import com.strangenaut.attendance.core.domain.model.ThemeMode
import com.strangenaut.attendance.home.presentation.HomeScreen
import com.strangenaut.attendance.home.presentation.HomeState
import com.strangenaut.attendance.settings.presentation.SettingsScreen
import com.strangenaut.attendance.settings.presentation.SettingsState
import com.strangenaut.attendance.statistics.navigation.StatisticsNavGraph
import com.strangenaut.attendance.statistics.presentation.StatisticsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TabsNavGraph(
    homeState: StateFlow<HomeState>,
    statisticsState: StateFlow<StatisticsState>,
    settingsState: StateFlow<SettingsState>,
    onSelectTheme: (themeMode: ThemeMode) -> Unit,
    onAddDiscipline: (discipline: String) -> Unit,
    onRemoveDiscipline: (discipline: String) -> Unit,
    onStartLesson: (discipline: String) -> Unit,
    onStopLesson: () -> Unit,
    onJoinLesson: (credentials: Credentials) -> Unit,
    onSignOut: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val homeTab = IconTabBarItem(
        route = Screen.Home.route,
        icon = painterResource(R.drawable.home)
    )
    val statisticsTab = IconTabBarItem(
        route = Screen.Statistics.route,
        icon = painterResource(R.drawable.statistics),
    )
    val settingsTab = IconTabBarItem(
        route = Screen.Settings.route,
        icon = painterResource(R.drawable.settings)
    )

    val tabBarItems = listOf(homeTab, statisticsTab, settingsTab)
    val navController = rememberNavController()

    val startDestinationIndex = maxOf(
        a = tabBarItems.indexOf(
            tabBarItems.find {
                it.route == navController.currentDestination?.route
            }
        ),
        b = 0
    )
    val currentTabIndex = MutableStateFlow(startDestinationIndex)

    Scaffold(
        bottomBar = {
            IconTabBar(
                currentTabIndex = currentTabIndex,
                iconTabBarItems = tabBarItems,
                navController = navController
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = homeTab.route
        ) {
            composable(homeTab.route) {
                HomeScreen(
                    homeState = homeState,
                    statisticsState = statisticsState,
                    onAddDiscipline = onAddDiscipline,
                    onRemoveDiscipline = onRemoveDiscipline,
                    onStartLesson = onStartLesson,
                    onStopLesson = onStopLesson,
                    onJoinLesson = onJoinLesson,
                    onNavigateToStatistics = {
                        currentTabIndex.update { 1 }
                        navController.setScreen(statisticsTab.route)
                    }
                )
            }
            composable(statisticsTab.route) {
                StatisticsNavGraph(
                    statisticsState = statisticsState,
                    currentUserEmail = homeState.value.currentUser.email
                )
            }
            composable(settingsTab.route) {
                SettingsScreen(
                    settingsState = settingsState,
                    onSelectTheme = { themeMode ->
                        onSelectTheme(themeMode)
                        currentTabIndex.update { 2 }
                    },
                    onSignOut = {
                        onSignOut()
                        onNavigateBack()
                    }
                )
            }
        }
    }
}