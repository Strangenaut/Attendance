package com.strangenaut.attendance.auth.domain.usecase

import com.strangenaut.attendance.auth.domain.repository.AuthRepository
import com.strangenaut.attendance.core.domain.model.Response

class SignIn(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String) {
        when (val signInResponse = repository.signInWithEmailAndPassword(email, password)) {
            is Response.Success -> Unit
            is Response.Failure -> throw Exception(signInResponse.message)
        }
    }
}