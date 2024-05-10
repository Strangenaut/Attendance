package com.strangenaut.attendance.home.presentation.host.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.presentation.components.AddButton
import com.strangenaut.attendance.core.presentation.components.LabeledTopBarWithNavBack
import com.strangenaut.attendance.core.presentation.components.RemovableIconTextButton
import com.strangenaut.attendance.home.presentation.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ChooseDiscipline(
    homeState: StateFlow<HomeState>,
    onChooseDiscipline: (discipline: String) -> Unit,
    onRemoveDiscipline: (discipline: String) -> Unit,
    onNavigateToAddDiscipline: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val collectedState = homeState.collectAsState()
    val state by remember {
        mutableStateOf(collectedState)
    }

    Scaffold(
        topBar = {
            LabeledTopBarWithNavBack(
                label = stringResource(R.string.choose_discipline),
                onNavigateBack = onNavigateBack
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            AddButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp
                    ),
                onClick = onNavigateToAddDiscipline
            )
            for (discipline in state.value.disciplines.reversed()) {
                RemovableIconTextButton(
                    text = discipline,
                    painter = painterResource(R.drawable.book),
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp
                    ),
                    onClick = {
                        onChooseDiscipline(discipline)
                    },
                    onClickRemove = {
                        onRemoveDiscipline(discipline)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ChooseDisciplePreview() {
    val state = HomeState(
        disciplines = setOf(
            "Linear Algebra",
            "Geometry",
            "English"
        )
    )

    ChooseDiscipline(
        homeState = MutableStateFlow(state),
        onChooseDiscipline = {},
        onRemoveDiscipline = {},
        onNavigateToAddDiscipline = {},
        onNavigateBack = {}
    )
}