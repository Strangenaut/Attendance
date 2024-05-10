package com.strangenaut.attendance.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.presentation.components.LabeledNumber
import com.strangenaut.attendance.core.ui.theme.SurfaceShape

@Composable
fun StatisticsButton(
    lessonsHosted: Int,
    lessonsAttended: Int,
    label: String,
    attendanceLabel: String,
    hostingLabel: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .clip(SurfaceShape)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.statistics),
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                LabeledNumber(
                    number = lessonsAttended,
                    label = attendanceLabel,
                    modifier = Modifier.weight(1f)
                )
                LabeledNumber(
                    number = lessonsHosted,
                    label = hostingLabel,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview
@Composable
private fun StatisticsButtonPreview() {
    StatisticsButton(
        lessonsHosted = 1,
        lessonsAttended = 3,
        attendanceLabel = "lessons attended",
        hostingLabel = "lessons conducted",
        label = "Statistics",
        onClick = {}
    )
}