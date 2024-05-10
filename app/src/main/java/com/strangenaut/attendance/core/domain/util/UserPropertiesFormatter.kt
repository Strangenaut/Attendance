package com.strangenaut.attendance.core.domain.util

import com.strangenaut.attendance.core.domain.model.User

object UserPropertiesFormatter {

    private const val FANCY_SEPARATOR = " • "

    fun User.getFancyUserProperties(): String {
        val properties = mutableListOf(
            this.group,
            this.department,
            this.school
        )

        properties.removeAll {
            it.isEmpty()
        }

        return properties.joinToString(FANCY_SEPARATOR)
    }
}