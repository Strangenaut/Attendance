package com.strangenaut.attendance.core.domain.repository

import com.strangenaut.attendance.core.domain.model.Response
import com.strangenaut.attendance.core.domain.model.User

interface UserRepository {

    suspend fun getUser(): Response

    suspend fun getDisciplines(): Response

    suspend fun addDiscipline(discipline: String): Response

    suspend fun removeDiscipline(discipline: String): Response
}