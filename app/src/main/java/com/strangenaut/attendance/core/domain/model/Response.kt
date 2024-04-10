package com.strangenaut.attendance.core.domain.model

sealed class Response {

    data class Success(
        val data: Any
    ): Response()

    data class Failure(
        val message: String
    ): Response()
}