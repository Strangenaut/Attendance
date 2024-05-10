package com.strangenaut.attendance.settings.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.core.ui.theme.SurfaceShape

@Composable
fun TextRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Surface(
        shape = SurfaceShape,
        color = if (selected)
            MaterialTheme.colorScheme.onSurface
        else
            MaterialTheme.colorScheme.surface,
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onSelect
        )
    ) {
        Text(
            text = text,
            color = if (selected)
                MaterialTheme.colorScheme.background
            else
                MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}