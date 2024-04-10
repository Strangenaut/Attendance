package com.strangenaut.attendance.auth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import com.strangenaut.attendance.auth.presentation.AuthState
import com.strangenaut.attendance.core.components.LabeledTextField
import com.strangenaut.attendance.core.components.TextButton
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ForgotPassword(
    authState: StateFlow<AuthState>,
    onSubmit: (email: String) -> Unit,
    onWaitForButtonToBecomeEnabled: (timeSeconds: Int) -> Unit
) {
    var email by remember {
        mutableStateOf("")
    }
    val initialTimeLeftSeconds = 60
    val collectedState = authState.collectAsState()
    val state by remember {
        mutableStateOf(collectedState)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LabeledTextField(
            initialValue = email,
            label = "Какой у вас электронный адрес?",
            hint = "Введите адрес",
            keyboardType = KeyboardType.Email,
            onValueChange = {
                email = it
            }
        )
        TextButton(
            text = "Отправить запрос",
            enabled = state.value.secondsLeftUntilPasswordReset <= 0,
            onClick = {
                onSubmit(email)
                onWaitForButtonToBecomeEnabled(initialTimeLeftSeconds)
            }
        )
        Text("До повторной отправки: ${state.value.secondsLeftUntilPasswordReset} с")
    }
}