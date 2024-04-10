package com.strangenaut.attendance.home.presentation

sealed class HomeEvent {

    data object GetUser: HomeEvent()

    data object GetDisciplines: HomeEvent()

    data object GetCurrentLesson: HomeEvent()

    data class AddDiscipline(val discipline: String): HomeEvent()

    data class StartLesson(val discipline: String): HomeEvent()

    data object StopLesson: HomeEvent()
}