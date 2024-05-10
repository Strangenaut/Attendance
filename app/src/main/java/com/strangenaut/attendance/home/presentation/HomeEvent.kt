package com.strangenaut.attendance.home.presentation

import com.strangenaut.attendance.core.domain.model.Credentials

sealed class HomeEvent {

    data object GetUser: HomeEvent()

    data object GetDisciplines: HomeEvent()

    data object GetCurrentLesson: HomeEvent()

    data class AddDiscipline(val discipline: String): HomeEvent()

    data class RemoveDiscipline(val discipline: String): HomeEvent()

    data class StartLesson(val discipline: String): HomeEvent()

    data object StopLesson: HomeEvent()

    data class JoinLesson(val credentials: Credentials): HomeEvent()
}
