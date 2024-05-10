package com.strangenaut.attendance.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LabeledNumber(
    number: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    val textColor = if (number > 0)
        MaterialTheme.colorScheme.onSurface
    else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.displaySmall,
            color = textColor
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}