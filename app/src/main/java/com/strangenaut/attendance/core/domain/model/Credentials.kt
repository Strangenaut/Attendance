package com.strangenaut.attendance.core.domain.model

data class Credentials (
    val host: String = "",
    val id: String = "",
    val token: String = ""
) {

    override fun toString(): String {
        return host + DELIMITER + id + DELIMITER + token
    }

    companion object {

        private const val DELIMITER = '/'

        fun convertFromString(credentials: String): Credentials {
            val host = credentials.substringBefore(DELIMITER)
            val id = credentials
                .substringAfter(DELIMITER)
                .substringBefore(DELIMITER)
            val token = credentials.substringAfterLast(DELIMITER)

            return Credentials(host = host, id = id, token = token)
        }
    }
}