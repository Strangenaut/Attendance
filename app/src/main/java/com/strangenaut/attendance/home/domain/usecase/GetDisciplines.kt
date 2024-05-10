package com.strangenaut.attendance.home.domain.usecase

import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.repository.UserRepository

class GetDisciplines(private val repository: UserRepository) {

    suspend operator fun invoke(): Set<String> {
        var disciplines: Set<String> = setOf()

        when (val getDisciplinesResponse = repository.getDisciplines()) {
            is Response.Success -> {
                val list = getDisciplinesResponse.data as Set<*>
                val disciplinesString = mutableSetOf<String>()

                for (discipline in list) {
                    disciplinesString += discipline.toString()
                }
                disciplines = disciplinesString
            }
            is Response.Failure -> throw Exception(getDisciplinesResponse.message)
        }

        return disciplines
    }
}