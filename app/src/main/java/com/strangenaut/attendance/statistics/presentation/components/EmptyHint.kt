package com.strangenaut.attendance.statistics.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R

@Composable
fun EmptyHint(modifier: Modifier = Modifier) {
    val emptyHintStyle = MaterialTheme.typography.bodyMedium.copy(
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Text(
        text = stringResource(R.string.empty),
        style = emptyHintStyle,
        modifier = modifier.padding(top = 16.dp)
    )
}