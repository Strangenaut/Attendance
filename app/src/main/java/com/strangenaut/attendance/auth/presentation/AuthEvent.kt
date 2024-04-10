package com.strangenaut.attendance.auth.presentation

sealed class AuthEvent {

    data class SignIn(
        val email: String,
        val password: String,
        val navigateToMainScreen: () -> Unit
    ) : AuthEvent()

    data class SendPasswordResetEmail(val email: String): AuthEvent()

    data class WaitForButtonToBecomeEnabled(val timeSeconds: Int): AuthEvent()

    data class SignUp(
        val email: String,
        val password: String,
        val passwordRepetition: String,
        val navigateToVerification: () -> Unit
    ) : AuthEvent()

    data object SendVerificationEmail : AuthEvent()

    data class WaitForVerification(val navigateToRegistration: () -> Unit) : AuthEvent()

    data class Register(
        val name: String,
        val surname: String,
        val school: String,
        val department: String,
        val group: String,
        val navigateToMainScreen: () -> Unit
    ) : AuthEvent()

    data class SignOut(val navigateToAuthScreen: () -> Unit) : AuthEvent()

    data object DeleteCurrentUser : AuthEvent()
}