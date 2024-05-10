package com.strangenaut.attendance.statistics.presentation

sealed class StatisticsEvent {

    data object GetPastLessons: StatisticsEvent()

    data object StartPastLessonsListening: StatisticsEvent()
}