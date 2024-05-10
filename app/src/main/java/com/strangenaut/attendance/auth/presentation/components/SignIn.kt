package com.strangenaut.attendance.auth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.presentation.components.ClickableText
import com.strangenaut.attendance.core.presentation.components.LabeledClickableText
import com.strangenaut.attendance.core.presentation.components.LabeledTextField
import com.strangenaut.attendance.core.presentation.components.TextButton

@Composable
fun SignIn(
    onSubmit: (email: String, password: String) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
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
        LabeledTextField(
            initialValue = password,
            label = stringResource(R.string.what_is_your_password),
            hint = stringResource(R.string.enter_password),
            keyboardType = KeyboardType.Password,
            onValueChange = {
                password = it
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(horizontal = 12.dp)
        ) {
            ClickableText(
                text = stringResource(R.string.forgot_password),
                textStyle = MaterialTheme.typography.bodySmall,
                onClick = onForgotPassword
            )
        }
        TextButton(
            text = stringResource(R.string.sign_in),
            onClick = {
                onSubmit(email, password)
            }
        )
        LabeledClickableText(
            label = stringResource(R.string.no_account),
            text = stringResource(R.string.register),
            textStyle = MaterialTheme.typography.bodySmall,
            onClick = onSignUp
        )
    }
}