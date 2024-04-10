package com.strangenaut.attendance.auth.presentation.util

sealed class Screen(val route: String) {
    data object SignIn: Screen("sign_in")
    data object SignUp: Screen("sign_up")
    data object VerifyEmail: Screen("verify_email")
    data object ForgotPassword: Screen("forgot_password")
    data object Registration: Screen("registration")
}