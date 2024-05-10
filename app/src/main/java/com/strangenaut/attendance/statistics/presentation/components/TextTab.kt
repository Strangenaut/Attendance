package com.strangenaut.attendance.statistics.presentation.components

import android.content.res.Resources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.core.presentation.components.HorizontalLine

@Composable
fun TextTab(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var textLength by remember {
            mutableStateOf(0.dp)
        }

        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 24.dp,
                    bottom = 4.dp
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .onSizeChanged {
                    val density = Resources.getSystem().displayMetrics.density
                    textLength = (it.width / density).dp
                }
        )
        if (selected) {
            HorizontalLine(
                height = 2.dp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .width(textLength)
                    .padding(bottom = 20.dp)
            )
        }
    }
}