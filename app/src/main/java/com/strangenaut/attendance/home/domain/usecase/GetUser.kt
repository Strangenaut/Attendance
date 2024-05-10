package com.strangenaut.attendance.home.domain.usecase

import com.strangenaut.attendance.core.domain.model.Response.Success
import com.strangenaut.attendance.core.domain.model.Response.Failure
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.domain.repository.UserRepository

class GetUser(private val repository: UserRepository) {

    suspend operator fun invoke(): User {
        var user = User()

        when (val getUserResponse = repository.getUser()) {
            is Success -> user = getUserResponse.data as User
            is Failure -> throw Exception(getUserResponse.message)
        }

        return user
    }
}