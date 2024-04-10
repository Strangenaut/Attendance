package com.strangenaut.attendance.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun HorizontalLine(
    modifier: Modifier = Modifier,
    height: Dp,
    color: Color,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color)
    ) {
        Spacer(modifier = Modifier.height(height))
    }
}