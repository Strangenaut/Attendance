package com.strangenaut.attendance.auth.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class WaitForButtonToBecomeEnabled {

    suspend operator fun invoke(
        timeSeconds: Int,
        onTimeUpdate: (timeLeft: Int) -> Unit
    ) {
        withContext(Dispatchers.Default) {
            var timeLeft = timeSeconds

            while (timeLeft-- > 0) {
                onTimeUpdate(timeLeft)
                delay(ONE_SECOND_MILLIS)
            }
        }
    }

    companion object {

        private const val ONE_SECOND_MILLIS = 1000L
    }
}