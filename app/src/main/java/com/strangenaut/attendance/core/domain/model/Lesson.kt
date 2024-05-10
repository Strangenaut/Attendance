package com.strangenaut.attendance.core.domain.model

data class Lesson(
    val discipline: String = "",
    val date: String = "",
    val time: String = "",
    val host: User? = null,
    val credentials: Credentials = Credentials(),
    val participants: List<Participant> = listOf()
)