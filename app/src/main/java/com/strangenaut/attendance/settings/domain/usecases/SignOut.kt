package com.strangenaut.attendance.settings.domain.usecases

import com.strangenaut.attendance.auth.domain.repository.AuthRepository

class SignOut(private val repository: AuthRepository) {

    operator fun invoke() {
        repository.signOut()
    }
}