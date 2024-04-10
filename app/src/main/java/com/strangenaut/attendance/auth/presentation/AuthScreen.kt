package com.strangenaut.attendance.auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.strangenaut.attendance.R
import com.strangenaut.attendance.auth.presentation.components.ForgotPassword
import com.strangenaut.attendance.auth.presentation.components.Registration
import com.strangenaut.attendance.auth.presentation.components.SignIn
import com.strangenaut.attendance.auth.presentation.components.SignUp
import com.strangenaut.attendance.auth.presentation.components.VerifyEmail
import com.strangenaut.attendance.auth.presentation.util.Screen
import com.strangenaut.attendance.core.navigation.util.setScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AuthScreen(
    authState: StateFlow<AuthState>,
    onSignIn: (email: String, password: String) -> Unit,
    onSendPasswordResetEmail: (email: String) -> Unit,
    onWaitForButtonToBecomeEnabled: (timeSeconds: Int) -> Unit,
    onSignUp: (
        email: String,
        password: String,
        passwordRepetition: String,
        navigateToVerification: () -> Unit
    ) -> Unit,
    onSendVerificationEmail: () -> Unit,
    onWaitForEmailVerification: (navigateToRegistration: () -> Unit) -> Unit,
    onRegister: (
        name: String,
        surname: String,
        school: String,
        department: String,
        group: String
    ) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.app_icon),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(0.35f)
        )
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screen.SignIn.route,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            composable(Screen.SignIn.route) {
                SignIn(
                    onSubmit = onSignIn,
                    onSignUp = {
                        navController.navigate(Screen.SignUp.route)
                    },
                    onForgotPassword = {
                        navController.navigate(Screen.ForgotPassword.route)
                    }
                )
            }
            composable(Screen.ForgotPassword.route) {
                ForgotPassword(
                    authState = authState,
                    onSubmit = onSendPasswordResetEmail,
                    onWaitForButtonToBecomeEnabled = onWaitForButtonToBecomeEnabled
                )
            }
            composable(Screen.SignUp.route) {
                SignUp(
                    onSubmit = { email, password, passwordRepetition ->
                        val navigateToVerification = {
                            navController.setScreen(Screen.VerifyEmail.route)
                        }
                        onSignUp(email, password, passwordRepetition, navigateToVerification)
                    }
                )
            }
            composable(Screen.VerifyEmail.route) {
                VerifyEmail(
                    onWaitForConfirmation = {
                        val navigateToRegistration = {
                            navController.setScreen(Screen.Registration.route)
                        }
                        onWaitForEmailVerification(navigateToRegistration)
                    },
                    onSubmitAgain = onSendVerificationEmail,
                )
            }
            composable(Screen.Registration.route) {
                Registration(
                    onSubmit = { name, surname, school, department, group ->
                        onRegister(name, surname, school, department, group)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {
    AuthScreen(
        authState = MutableStateFlow(AuthState()),
        onSignIn = { _, _ -> },
        onSendPasswordResetEmail = { _ -> },
        onWaitForButtonToBecomeEnabled = { _ -> },
        onSignUp = { _, _, _, _ -> },
        onSendVerificationEmail = {},
        onWaitForEmailVerification = {},
        onRegister = { _, _, _, _, _ -> }
    )
}