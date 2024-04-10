package com.strangenaut.attendance.auth.presentation

data class AuthState(
    val isUserAlreadySignedIn: Boolean = false,
    val secondsLeftUntilPasswordReset: Int = 0,
)