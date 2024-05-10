package com.strangenaut.attendance.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun ClickableText(
    text: String,
    textStyle: TextStyle,
    onClick: () -> Unit
) {
    Text(
        text = text,
        style = textStyle,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.clickable {
            onClick()
        }
    )
}