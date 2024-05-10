package com.strangenaut.attendance.auth.domain.usecase

import com.strangenaut.attendance.auth.domain.repository.AuthRepository
import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.model.User

class Register(private val repository: AuthRepository) {

    suspend operator fun invoke(
        name: String,
        surname: String,
        school: String,
        department: String,
        group: String
    ) {
        val email = repository.currentUserEmail ?: ""

        val user = User(email, name, surname, school, department, group)

        when (val registerResponse = repository.registerAccount(user)) {
            is Response.Success -> Unit
            is Response.Failure -> throw Exception(registerResponse.message)
        }
    }
}