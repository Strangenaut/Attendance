package com.strangenaut.attendance.auth.domain.usecase

import com.strangenaut.attendance.auth.domain.repository.AuthRepository
import com.strangenaut.attendance.core.domain.model.Response

class SendPasswordResetEmail(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String) {
        when (val passwordResetResponse = repository.sendPasswordResetEmail(email)) {
            is Response.Success -> Unit
            is Response.Failure -> throw Exception(passwordResetResponse.message)
        }
    }
}