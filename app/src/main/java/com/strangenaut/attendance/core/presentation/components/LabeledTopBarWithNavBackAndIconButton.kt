package com.strangenaut.attendance.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun LabeledTopBarWithNavBackAndIconButton(
    label: String,
    iconPainter: Painter,
    onClick: () -> Unit,
    onNavigateBack: () -> Unit
) {
    TopBar(alignment = Alignment.Start) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ClickableIcon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    onClick = onNavigateBack,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            ClickableIcon(
                painter = iconPainter,
                onClick = onClick,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}