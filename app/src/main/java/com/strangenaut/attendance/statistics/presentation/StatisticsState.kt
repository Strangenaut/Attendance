package com.strangenaut.attendance.statistics.presentation

import com.strangenaut.attendance.core.domain.model.Lesson
import com.strangenaut.attendance.statistics.navigation.TabScreen

data class StatisticsState (
    val lessons: List<Lesson> = listOf()
)