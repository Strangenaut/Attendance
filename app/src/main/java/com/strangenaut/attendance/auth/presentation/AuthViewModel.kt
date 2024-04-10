package com.strangenaut.attendance.auth.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strangenaut.attendance.core.domain.model.User
import com.strangenaut.attendance.core.domain.model.Response.Success
import com.strangenaut.attendance.core.domain.model.Response.Failure
import com.strangenaut.attendance.auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        _state.update {
            it.copy(isUserAlreadySignedIn = authRepository.isUserAlreadySignedIn)
        }
    }

    fun onEvent(event: AuthEvent) {
        when(event) {
            is AuthEvent.SignIn -> {
                viewModelScope.launch {
                    val signInResponse = authRepository.signInWithEmailAndPassword(
                        event.email,
                        event.password
                    )

                    when (signInResponse) {
                        is Success -> event.navigateToMainScreen()
                        is Failure -> _error.postValue(signInResponse.message)
                    }
                }
            }
            is AuthEvent.SendPasswordResetEmail -> {
                viewModelScope.launch {
                    val passwordResetResponse = authRepository.sendPasswordResetEmail(event.email)

                    if (passwordResetResponse is Failure) {
                        _error.postValue(passwordResetResponse.message)
                    }
                }
            }
            is AuthEvent.WaitForButtonToBecomeEnabled -> {
                viewModelScope.launch(Dispatchers.Default) {
                    var timeLeft = event.timeSeconds

                    while (timeLeft-- > 0) {
                        _state.update {
                            it.copy(secondsLeftUntilPasswordReset = timeLeft)
                        }
                        delay(1000)
                    }
                }
            }
            is AuthEvent.SignUp -> {
                viewModelScope.launch {
                    if (event.password != event.passwordRepetition) {
                        _error.postValue("Пароли не совпадают")
                        return@launch
                    }

                    val signUpResponse = authRepository.signUpWithEmailAndPassword(
                        event.email,
                        event.password
                    )
                    val verificationResponse = authRepository.sendEmailVerification()

                    if (signUpResponse is Success && verificationResponse is Success) {
                        event.navigateToVerification()
                    } else if (signUpResponse is Failure) {
                        _error.postValue(signUpResponse.message)
                    } else if (verificationResponse is Failure) {
                        _error.postValue(verificationResponse.message)
                    }
                }
            }
            is AuthEvent.SendVerificationEmail -> {
                viewModelScope.launch {
                    val verificationResponse = authRepository.sendEmailVerification()

                    if (verificationResponse is Failure) {
                        _error.postValue(verificationResponse.message)
                    }
                }
            }
            is AuthEvent.WaitForVerification -> {
                viewModelScope.launch {
                    while (!authRepository.isUserEmailVerified) {
                        authRepository.reloadFirebaseUser()
                        delay(3000)
                    }
                    event.navigateToRegistration()
                }
            }
            is AuthEvent.Register -> {
                viewModelScope.launch {
                    val email = authRepository.currentUser?.email

                    if (email == null) {
                        _error.postValue("Ошибка")
                        return@launch
                    }

                    val user = User(
                        email,
                        event.name,
                        event.surname,
                        event.school,
                        event.department,
                        event.group
                    )
                    
                    when (val registerResponse = authRepository.registerAccount(user)) {
                        is Success -> event.navigateToMainScreen()
                        is Failure -> _error.postValue(registerResponse.message)
                    }
                }
            }
            is AuthEvent.SignOut -> {
                authRepository.signOut()
                _state.update {
                    AuthState()
                }
                event.navigateToAuthScreen()
            }
            is AuthEvent.DeleteCurrentUser -> {
                viewModelScope.launch {
                    val deletionResponse = authRepository.revokeAccess()

                    if (deletionResponse is Failure) {
                        _error.postValue(deletionResponse.message)
                    }
                }
            }
        }
    }
}