package com.strangenaut.attendance.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.domain.model.ThemeMode
import com.strangenaut.attendance.core.presentation.components.HorizontalLine
import com.strangenaut.attendance.core.presentation.components.LabeledTopBar
import com.strangenaut.attendance.core.presentation.components.MenuIconItem
import com.strangenaut.attendance.settings.presentation.components.ThemeSelectionTextRadioButtons
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SettingsScreen(
    settingsState: StateFlow<SettingsState>,
    onSelectTheme: (themeMode: ThemeMode) -> Unit,
    onSignOut: () -> Unit
) {
    val collectedState = settingsState.collectAsState()
    val state by remember {
        mutableStateOf(collectedState)
    }

    val context = LocalContext.current
    val version = context
        .packageManager
        .getPackageInfo(context.packageName, 0)
        .versionName

    Scaffold(
        topBar = {
            LabeledTopBar(label = stringResource(R.string.settings))
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(it)) {
            MenuIconItem(
                label = stringResource(R.string.personal_info),
                icon = painterResource(R.drawable.person),
                showArrow = true,
                onClick = {}
            )
            HorizontalLine(
                height = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            )
            MenuIconItem(
                label = stringResource(R.string.theme),
                icon = painterResource(R.drawable.night)
            )
            ThemeSelectionTextRadioButtons(
                selectedValue = state.value.themeMode,
                onSelectTheme = onSelectTheme
            )
            MenuIconItem(
                label = "${stringResource(R.string.app_version)} $version",
                icon = painterResource(R.drawable.info)
            )
            HorizontalLine(
                height = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            )
            MenuIconItem(
                label = stringResource(R.string.sign_out),
                icon = painterResource(R.drawable.quit),
                iconColor = MaterialTheme.colorScheme.error,
                onClick = onSignOut
            )
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(
        settingsState = MutableStateFlow(SettingsState()),
        onSelectTheme = {},
        onSignOut = {}
    )
}