package com.strangenaut.attendance.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.strangenaut.attendance.core.navigation.util.setScreen
import com.strangenaut.attendance.core.presentation.util.printMessage
import com.strangenaut.attendance.home.presentation.HomeEvent
import com.strangenaut.attendance.home.presentation.HomeViewModel
import com.strangenaut.attendance.settings.presentation.SettingsEvent
import com.strangenaut.attendance.settings.presentation.SettingsViewModel
import com.strangenaut.attendance.statistics.presentation.StatisticsViewModel

@Composable
fun MainNavGraph(
    authViewModel: AuthViewModel,
    settingsViewModel: SettingsViewModel
) {
    val navController = rememberNavController()
    var startDestination = Screen.Auth.route
    val authState by authViewModel.state.collectAsStateWithLifecycle()

    val homeViewModel = hiltViewModel<HomeViewModel>()
    val statisticsViewModel = hiltViewModel<StatisticsViewModel>()

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
                            password,
                            onNavigateToMainScreen = {
                                navController.setScreen(Screen.Main.route)
                            }
                        )
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
                onSignUp = { email, password, passwordRepetition, onNavigateToVerification ->
                    authViewModel.onEvent(
                        AuthEvent.SignUp(
                            email,
                            password,
                            passwordRepetition,
                            onNavigateToVerification
                        )
                    )
                },
                onSendVerificationEmail = {
                    authViewModel.onEvent(AuthEvent.SendVerificationEmail)
                },
                onWaitForEmailVerification = { onNavigateToRegistration ->
                    authViewModel.onEvent(
                        AuthEvent.WaitForVerification(onNavigateToRegistration)
                    )
                },
                onRegister = { name, surname, school, department, group ->
                    authViewModel.onEvent(
                        AuthEvent.Register(
                            name,
                            surname,
                            school,
                            department,
                            group,
                            onNavigateToMainScreen = {
                                navController.setScreen(Screen.Main.route)
                            }
                        )
                    )
                }
            )
        }
        composable(Screen.Main.route) {
            val context = LocalContext.current
            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(key1 = Unit) {
                homeViewModel.initializeState()
                statisticsViewModel.initializeState()
            }

            homeViewModel.error.observe(lifecycleOwner) { message ->
                printMessage(context, message)
            }

            statisticsViewModel.error.observe(lifecycleOwner) { message ->
                printMessage(context, message)
            }

            TabsNavGraph(
                homeState = homeViewModel.state,
                statisticsState = statisticsViewModel.state,
                settingsState = settingsViewModel.state,
                onSelectTheme = { themeMode ->
                    settingsViewModel.onEvent(SettingsEvent.SelectTheme(themeMode))
                },
                onAddDiscipline = { discipline ->
                    homeViewModel.onEvent(HomeEvent.AddDiscipline(discipline))
                },
                onRemoveDiscipline = { discipline ->
                    homeViewModel.onEvent(HomeEvent.RemoveDiscipline(discipline))
                },
                onStartLesson = { discipline ->
                    homeViewModel.onEvent(HomeEvent.StartLesson(discipline))
                },
                onStopLesson = {
                    homeViewModel.onEvent(HomeEvent.StopLesson)
                },
                onJoinLesson = { credentials ->
                    homeViewModel.onEvent(HomeEvent.JoinLesson(credentials))
                },
                onSignOut = {
                    settingsViewModel.onEvent(SettingsEvent.SignOut)
                },
                onNavigateBack = {
                    navController.setScreen(Screen.Auth.route)
                }
            )
        }
    }
}