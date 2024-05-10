package com.strangenaut.attendance.home.domain.usecase

import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.repository.UserRepository

class AddDiscipline(private val repository: UserRepository) {

    suspend operator fun invoke(discipline: String) {
        when (val addDisciplineResponse = repository.addDiscipline(discipline)) {
            is Response.Success -> Unit
            is Response.Failure -> throw Exception(addDisciplineResponse.message)
        }
    }
}