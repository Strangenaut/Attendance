package com.strangenaut.attendance.auth.domain.usecase

import com.strangenaut.attendance.auth.domain.repository.AuthRepository
import com.strangenaut.attendance.core.domain.model.Response

class SignUp(private val repository: AuthRepository) {

    suspend operator fun invoke(
        email: String,
        password: String,
        passwordRepetition: String,
    ) {
        when (
            val signUpResponse = repository.signUpWithEmailAndPassword(
                email,
                password,
                passwordRepetition
            )
        ) {
            is Response.Success -> Unit
            is Response.Failure -> throw Exception(signUpResponse.message)
        }

        when (val verificationResponse = repository.sendEmailVerification()) {
            is Response.Success -> Unit
            is Response.Failure -> throw Exception(verificationResponse.message)
        }
    }
}