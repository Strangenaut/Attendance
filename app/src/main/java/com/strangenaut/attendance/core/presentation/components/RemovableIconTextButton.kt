package com.strangenaut.attendance.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.ui.theme.SurfaceShape

@Composable
fun RemovableIconTextButton(
    text: String,
    painter: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onClickRemove: () -> Unit,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Button(
        onClick = onClick,
        shape = SurfaceShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = modifier.height(50.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(0.5f)
                .padding(start = 16.dp)
        )
        Icon(
            imageVector = Icons.Filled.Clear,
            contentDescription = null,
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    onClickRemove()
                }
            )
        )
    }
}

@Preview
@Composable
fun RemovableIconTextButtonPreview() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        RemovableIconTextButton(
            text = "Linear algebra",
            painter = painterResource(R.drawable.book),
            onClick = {},
            onClickRemove = {}
        )
    }
}