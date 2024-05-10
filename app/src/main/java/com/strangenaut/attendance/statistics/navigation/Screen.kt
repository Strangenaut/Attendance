package com.strangenaut.attendance.statistics.navigation

sealed class Screen(val route: String) {

    data object Statistics: Screen("statistics_screen")

    data object HostedLesson: Screen("hosted_lesson")

    data object AttendedLesson: Screen("attended_lesson")

    data object DisciplineAttendances: Screen("discipline_attendances")
}