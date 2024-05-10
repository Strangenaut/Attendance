package com.strangenaut.attendance.auth.domain.usecase

import com.strangenaut.attendance.auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class WaitForEmailVerification(private val repository: AuthRepository) {

    suspend operator fun invoke(timeIntervalMillis: Long) {
        while (!repository.isUserEmailVerified) {
            repository.reloadFirebaseUser()
            delay(timeIntervalMillis)
        }
    }
}