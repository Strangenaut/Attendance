package com.strangenaut.attendance.statistics.domain.usecases

data class StatisticsUseCases(
    val getPastLessons: GetPastLessons,
    val startPastLessonsListening: StartPastLessonsListening
)