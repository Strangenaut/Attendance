package com.strangenaut.attendance.core.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.core.presentation.components.TopBar

@Composable
fun LabeledTopBar(
    label: String,
    onNavigateBack: () -> Unit
) {
    TopBar(alignment = Alignment.Start) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ClickableIcon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                onClick = onNavigateBack,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}