package com.strangenaut.attendance.core.navigation

sealed class Screen(val route: String) {
    data object Auth: Screen("auth")

    data object Main: Screen("main")

    data object Home: Screen("home")

    data object Statistics: Screen("statistics")

    data object Settings: Screen("settings")
}