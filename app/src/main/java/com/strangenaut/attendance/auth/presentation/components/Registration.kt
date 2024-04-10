package com.strangenaut.attendance.auth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.strangenaut.attendance.core.components.LabeledTextField
import com.strangenaut.attendance.core.components.TextButton

@Composable
fun Registration(
    onSubmit: (
        name: String,
        surname: String,
        school: String,
        department: String,
        group: String
    ) -> Unit
) {
    var name by remember {
        mutableStateOf("")
    }
    var surname by remember {
        mutableStateOf("")
    }
    var school by remember {
        mutableStateOf("")
    }
    var department by remember {
        mutableStateOf("")
    }
    var group by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        LabeledTextField(
            initialValue = name,
            label = "Имя",
            hint = "Введите имя",
            keyboardType = KeyboardType.Email,
            onValueChange = {
                name = it
            }
        )
        LabeledTextField(
            initialValue = surname,
            label = "Фамилия",
            hint = "Введите фамилию",
            keyboardType = KeyboardType.Password,
            onValueChange = {
                surname = it
            }
        )
        LabeledTextField(
            initialValue = school,
            label = "Школа / университет",
            hint = "Введите школу",
            keyboardType = KeyboardType.Password,
            onValueChange = {
                school = it
            }
        )
        LabeledTextField(
            initialValue = department,
            label = "Кафедра / институт / факультет",
            hint = "Введите подразделение",
            keyboardType = KeyboardType.Password,
            onValueChange = {
                department = it
            }
        )
        LabeledTextField(
            initialValue = group,
            label = "Класс / группа",
            hint = "Введите класс / группу",
            keyboardType = KeyboardType.Password,
            onValueChange = {
                group = it
            }
        )
        TextButton(
            text = "Отправить",
            onClick = {
                onSubmit(name, surname, school, department, group)
            }
        )
    }
}

@Preview
@Composable
private fun RegistrationPreview() {
    Registration(
        onSubmit = { _, _, _, _, _ -> }
    )
}