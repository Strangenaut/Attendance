package com.strangenaut.attendance.home.presentation

import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.home.model.Lesson

data class HomeState (
    val currentUser: User = User(),
    val disciplines: List<String> = listOf(),
    val currentLesson: Lesson? = null
)