package com.strangenaut.attendance.auth.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strangenaut.attendance.auth.domain.usecase.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        val isUserAlreadySignedIn = authUseCases.getUserState()
        _state.update {
            it.copy(isUserAlreadySignedIn = isUserAlreadySignedIn)
        }
    }

    fun onEvent(event: AuthEvent) {
        viewModelScope.launch {
            try {
                executeEvent(event)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    private suspend fun executeEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignIn -> {
                authUseCases.signIn(event.email, event.password)

                event.onNavigateToMainScreen()
            }
            is AuthEvent.SendPasswordResetEmail -> {
                authUseCases.sendPasswordResetEmail(event.email)
            }
            is AuthEvent.WaitForButtonToBecomeEnabled -> {
                authUseCases.waitForButtonToBecomeEnabled(
                    timeSeconds = event.timeSeconds,
                    onTimeUpdate = { timeLeft ->
                        _state.update {
                            it.copy(secondsLeftUntilPasswordReset = timeLeft)
                        }
                    }
                )
            }
            is AuthEvent.SignUp -> {
                authUseCases.signUp(event.email, event.password, event.passwordRepetition)
                event.onNavigateToVerification()
            }
            is AuthEvent.SendVerificationEmail -> {
                authUseCases.sendVerificationEmail()
            }
            is AuthEvent.WaitForVerification -> {
                withContext(Dispatchers.Default) {
                    authUseCases.waitForEmailVerification(
                        SEND_VERIFICATION_EMAIL_INTERVAL_MILLIS
                    )
                }
                event.onNavigateToRegistration()
            }
            is AuthEvent.Register -> {
                authUseCases.register(
                    event.name,
                    event.surname,
                    event.school,
                    event.department,
                    event.group
                )
                event.onNavigateToMainScreen()
            }
            is AuthEvent.DeleteCurrentUser -> {
                authUseCases.deleteCurrentUser()
            }
        }
    }

    companion object {

        private const val SEND_VERIFICATION_EMAIL_INTERVAL_MILLIS = 3000L
    }
}