package com.strangenaut.attendance.home.presentation.host.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.core.components.LabeledTextField
import com.strangenaut.attendance.core.components.LabeledTopBar
import com.strangenaut.attendance.core.components.TextButton

@Composable
fun AddDiscipline(
    onAddDiscipline: (discipline: String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var discipline by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            LabeledTopBar(
                label = "Добавить новый предмет",
                onNavigateBack = onNavigateBack
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            LabeledTextField(
                initialValue = discipline,
                label = "Как называется предмет?",
                hint = "Введите название предмета",
                modifier = Modifier.padding(top = 16.dp),
                onValueChange = { value ->
                    discipline = value
                }
            )
            TextButton(
                text = "Сохранить",
                onClick = {
                    if (discipline.isNotEmpty()) {
                        onAddDiscipline(discipline)
                        onNavigateBack()
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun AddDisciplinePreview() {
    AddDiscipline(
        onAddDiscipline = {},
        onNavigateBack = {}
    )
}