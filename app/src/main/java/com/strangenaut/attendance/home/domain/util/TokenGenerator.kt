package com.strangenaut.attendance.home.domain.util

import java.security.SecureRandom

object TokenGenerator {

    private const val TOKEN_LENGTH = 16

    fun generateToken(): String {
        val charset = ('A'..'Z') +
                ('a'..'z') +
                ('0'..'9') +
                listOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')')

        val secureRandom = SecureRandom()

        return (1..TOKEN_LENGTH)
            .map { charset[secureRandom.nextInt(charset.size)] }
            .joinToString("")
    }
}