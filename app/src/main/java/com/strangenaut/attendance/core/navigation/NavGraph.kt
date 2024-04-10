package com.strangenaut.attendance.core.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.strangenaut.attendance.auth.presentation.AuthEvent
import com.strangenaut.attendance.auth.presentation.AuthScreen
import com.strangenaut.attendance.auth.presentation.AuthViewModel
import com.strangenaut.attendance.core.presentation.MainScreen
import com.strangenaut.attendance.core.navigation.util.setScreen
import com.strangenaut.attendance.home.presentation.HomeEvent
import com.strangenaut.attendance.home.presentation.HomeViewModel

@Composable
fun NavGraph(
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    var startDestination = Screen.Auth.route
    val authState by authViewModel.state.collectAsStateWithLifecycle()

    if (authState.isUserAlreadySignedIn) {
        startDestination = Screen.Main.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Auth.route) {
            AuthScreen(
                authState = authViewModel.state,
                onSignIn = { email, password ->
                    authViewModel.onEvent(
                        AuthEvent.SignIn(
                            email,
                            password
                        ) {
                            navController.setScreen(Screen.Main.route)
                        }
                    )
                },
                onSendPasswordResetEmail = { email ->
                    authViewModel.onEvent(
                        AuthEvent.SendPasswordResetEmail(email)
                    )
                },
                onWaitForButtonToBecomeEnabled = { timeSeconds ->
                    authViewModel.onEvent(
                        AuthEvent.WaitForButtonToBecomeEnabled(timeSeconds)
                    )
                },
                onSignUp = { email, password, passwordRepetition, navigateToVerification ->
                    authViewModel.onEvent(
                        AuthEvent.SignUp(
                            email,
                            password,
                            passwordRepetition,
                            navigateToVerification
                        )
                    )
                },
                onSendVerificationEmail = {
                    authViewModel.onEvent(AuthEvent.SendVerificationEmail)
                },
                onWaitForEmailVerification = { navigateToRegistration ->
                    authViewModel.onEvent(
                        AuthEvent.WaitForVerification(navigateToRegistration)
                    )
                },
                onRegister = { name, surname, school, department, group ->
                    authViewModel.onEvent(
                        AuthEvent.Register(
                            name,
                            surname,
                            school,
                            department,
                            group
                        ) {
                            navController.setScreen(Screen.Main.route)
                        }
                    )
                }
            )
        }
        composable(Screen.Main.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val context = LocalContext.current
            val lifecycleOwner = LocalLifecycleOwner.current

            homeViewModel.error.observe(lifecycleOwner) { message ->
                if (message.isNotEmpty()) {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                }
            }

            MainScreen(
                homeState = homeViewModel.state,
                onAddDiscipline = {
                    homeViewModel.onEvent(HomeEvent.AddDiscipline(it))
                },
                onStartLesson = {
                    homeViewModel.onEvent(HomeEvent.StartLesson(it))
                },
                onStopLesson = {
                    homeViewModel.onEvent(HomeEvent.StopLesson)
                }
            )
        }
    }
}