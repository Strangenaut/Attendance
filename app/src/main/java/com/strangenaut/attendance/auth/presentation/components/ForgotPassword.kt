package com.strangenaut.attendance.auth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.strangenaut.attendance.R
import com.strangenaut.attendance.auth.presentation.AuthState
import com.strangenaut.attendance.core.presentation.components.LabeledTextField
import com.strangenaut.attendance.core.presentation.components.TextButton
import kotlinx.coroutines.flow.StateFlow

private const val SEND_FORGOT_PASSWORD_EMAIL_INTERVAL_SECONDS = 60

@Composable
fun ForgotPassword(
    authState: StateFlow<AuthState>,
    onSubmit: (email: String) -> Unit,
    onWaitForButtonToBecomeEnabled: (timeSeconds: Int) -> Unit
) {
    var email by remember {
        mutableStateOf("")
    }
    val collectedState = authState.collectAsState()
    val state by remember {
        mutableStateOf(collectedState)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LabeledTextField(
            initialValue = email,
            label = stringResource(R.string.what_is_your_email),
            hint = stringResource(R.string.enter_email),
            keyboardType = KeyboardType.Email,
            onValueChange = {
                email = it
            }
        )
        TextButton(
            text = stringResource(R.string.send_request),
            enabled = state.value.secondsLeftUntilPasswordReset <= 0,
            onClick = {
                onSubmit(email)
                onWaitForButtonToBecomeEnabled(SEND_FORGOT_PASSWORD_EMAIL_INTERVAL_SECONDS)
            }
        )
        if (state.value.secondsLeftUntilPasswordReset != 0) {
            Text(
                text = stringResource(
                    R.string.until_resending,
                    state.value.secondsLeftUntilPasswordReset
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}