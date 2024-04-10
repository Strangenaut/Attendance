package com.strangenaut.attendance.core.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun LabeledClickableText(
    label: String,
    text: String,
    textStyle: TextStyle,
    onClick: () -> Unit
) {
    Row {
       Text(
           text = label,
           style = textStyle
       )
       Spacer(modifier = Modifier.width(8.dp))
       ClickableText(
           text = text,
           textStyle = textStyle,
           onClick = onClick
       )
    }
}