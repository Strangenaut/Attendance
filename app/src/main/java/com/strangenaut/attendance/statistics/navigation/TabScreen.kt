package com.strangenaut.attendance.statistics.navigation

sealed class TabScreen {

    data object History: Screen("history")

    data object Attendances: Screen("attendances")
}