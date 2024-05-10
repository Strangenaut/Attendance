package com.strangenaut.attendance.settings.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.domain.model.ThemeMode

@Composable
fun ThemeSelectionTextRadioButtons(
    selectedValue: ThemeMode,
    onSelectTheme: (themeMode: ThemeMode) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        TextRadioButton(
            text = stringResource(R.string.system_theme),
            selected = selectedValue == ThemeMode.SYSTEM,
            onSelect = {
                onSelectTheme(ThemeMode.SYSTEM)
            },
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        TextRadioButton(
            text = stringResource(R.string.light_theme),
            selected = selectedValue == ThemeMode.LIGHT,
            onSelect = {
                onSelectTheme(ThemeMode.LIGHT)
            },
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        TextRadioButton(
            text = stringResource(R.string.dark_theme),
            selected = selectedValue == ThemeMode.DARK,
            onSelect = {
                onSelectTheme(ThemeMode.DARK)
            },
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}