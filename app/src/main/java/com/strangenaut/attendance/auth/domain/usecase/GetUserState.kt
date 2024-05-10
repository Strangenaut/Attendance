package com.strangenaut.attendance.auth.domain.usecase

import com.strangenaut.attendance.auth.domain.repository.AuthRepository

class GetUserState(private val repository: AuthRepository) {

    operator fun invoke(): Boolean {
        return repository.isUserAlreadySignedIn
    }
}