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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.presentation.components.LabeledTextField
import com.strangenaut.attendance.core.presentation.components.TextButton

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
            label = stringResource(R.string.name),
            hint = stringResource(R.string.enter_name),
            keyboardType = KeyboardType.Email,
            onValueChange = {
                name = it
            }
        )
        LabeledTextField(
            initialValue = surname,
            label = stringResource(R.string.surname),
            hint = stringResource(R.string.enter_surname),
            keyboardType = KeyboardType.Password,
            onValueChange = {
                surname = it
            }
        )
        LabeledTextField(
            initialValue = school,
            label = stringResource(R.string.school),
            hint = stringResource(R.string.enter_school),
            keyboardType = KeyboardType.Password,
            onValueChange = {
                school = it
            }
        )
        LabeledTextField(
            initialValue = department,
            label = stringResource(R.string.department),
            hint = stringResource(R.string.enter_department),
            keyboardType = KeyboardType.Password,
            onValueChange = {
                department = it
            }
        )
        LabeledTextField(
            initialValue = group,
            label = stringResource(R.string.group),
            hint = stringResource(R.string.enter_group),
            keyboardType = KeyboardType.Password,
            onValueChange = {
                group = it
            }
        )
        TextButton(
            text = stringResource(R.string.register),
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