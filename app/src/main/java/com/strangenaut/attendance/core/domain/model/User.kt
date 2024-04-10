package com.strangenaut.attendance.core.domain.model

data class User(
    val email: String = "",
    val name: String = "",
    val surname: String = "",
    val school: String = "",
    val department: String = "",
    val group: String = ""
)