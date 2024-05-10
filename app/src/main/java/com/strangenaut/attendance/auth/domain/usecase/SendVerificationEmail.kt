package com.strangenaut.attendance.auth.domain.usecase

import com.strangenaut.attendance.auth.domain.repository.AuthRepository
import com.strangenaut.attendance.core.domain.model.Response

class SendVerificationEmail(private val repository: AuthRepository) {

    suspend operator fun invoke() {
        when(val verificationResponse = repository.sendEmailVerification()) {
            is Response.Success -> Unit
            is Response.Failure -> throw Exception(verificationResponse.message)
        }
    }
}