package com.strangenaut.attendance.core.navigation

sealed class Screen(val route: String) {
    data object Auth: Screen("auth")
    data object Main: Screen("main")
}