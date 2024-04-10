package com.strangenaut.attendance.auth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.strangenaut.attendance.core.components.LabeledTextField
import com.strangenaut.attendance.core.components.TextButton

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
            label = "Какой у вас электронный адрес?",
            hint = "Введите адрес",
            keyboardType = KeyboardType.Email,
            onValueChange = {
                email = it
            }
        )
        LabeledTextField(
            initialValue = password,
            label = "Придумайте пароль для входа",
            hint = "Введите пароль",
            keyboardType = KeyboardType.Password,
            onValueChange = {
                password = it
            }
        )
        LabeledTextField(
            initialValue = password,
            label = "Повторите ввод пароль для входа",
            hint = "Введите пароль повторно",
            keyboardType = KeyboardType.Password,
            onValueChange = {
                passwordRepetition= it
            }
        )
        TextButton(
            text = "Отправить",
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