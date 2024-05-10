package com.strangenaut.attendance.home.domain.usecase

data class HomeUseCases (
    val getUser: GetUser,
    val getDisciplines: GetDisciplines,
    val addDiscipline: AddDiscipline,
    val removeDiscipline: RemoveDiscipline,
    val getCurrentLesson: GetCurrentLesson,
    val startLesson: StartLesson,
    val startCurrentLessonListening: StartCurrentLessonListening,
    val updateCredentials: UpdateCredentials,
    val stopCurrentLessonListening: StopCurrentLessonListening,
    val stopLesson: StopLesson,
    val joinLesson: JoinLesson
)