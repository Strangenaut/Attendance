package com.strangenaut.attendance.core.domain.model

data class Participant (
    val user: User? = null,
    val attendanceTime: String = "",
) {

    override fun toString(): String {
        return "${user?.group} ${user?.name} ${user?.surname} - $attendanceTime"
    }
}