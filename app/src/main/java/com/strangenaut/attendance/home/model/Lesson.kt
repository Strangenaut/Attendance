package com.strangenaut.attendance.home.model

import com.strangenaut.attendance.core.domain.model.User

data class Lesson(
    val discipline: String = "",
    val date: String = "",
    val time: String = "",
    val host: User? = null,
    val credentials: Credentials = Credentials(),
    val members: Map<User, String> = mutableMapOf()
)