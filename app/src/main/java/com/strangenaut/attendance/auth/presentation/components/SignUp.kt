package com.strangenaut.attendance.auth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.presentation.components.LabeledTextField
import com.strangenaut.attendance.core.presentation.components.TextButton

@Composable
fun SignUp(
    onSubmit: (email: String, password: String, passwordRepetition: String) -> Unit
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordRepetition by remember {
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
            label = stringResource(R.string.create_password),
            hint = stringResource(R.string.enter_password),
            keyboardType = KeyboardType.Password,
            onValueChange = {
                password = it
            }
        )
        LabeledTextField(
            initialValue = password,
            label = stringResource(R.string.repeat_created_password),
            hint = stringResource(R.string.repeat_password),
            keyboardType = KeyboardType.Password,
            onValueChange = {
                passwordRepetition= it
            }
        )
        TextButton(
            text = stringResource(R.string.register),
            onClick = {
                onSubmit(email, password, passwordRepetition)
            }
        )
    }
}

@Preview
@Composable
private fun SignUpPreview() {
    SignUp(onSubmit = { _, _, _ ->  })
}