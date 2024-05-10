package com.strangenaut.attendance.home.domain.usecase

import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.repository.UserRepository

class RemoveDiscipline(private val repository: UserRepository) {

    suspend operator fun invoke(discipline: String) {
        when (val removeDisciplineResponse = repository.removeDiscipline(discipline)) {
            is Response.Success -> Unit
            is Response.Failure -> throw Exception(removeDisciplineResponse.message)
        }
    }
}