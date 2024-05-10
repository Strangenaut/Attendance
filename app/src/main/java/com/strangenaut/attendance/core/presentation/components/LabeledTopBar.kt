package com.strangenaut.attendance.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LabeledTopBar(label: String) {
    TopBar(alignment = Alignment.Start) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(72.dp)
        ) {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}