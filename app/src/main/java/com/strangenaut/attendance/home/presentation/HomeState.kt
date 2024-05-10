package com.strangenaut.attendance.home.presentation

import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.domain.model.Lesson

data class HomeState (
    val currentUser: User = User(),
    val disciplines: Set<String> = setOf(),
    val currentLesson: Lesson? = null
)