package com.strangenaut.attendance.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.components.LabeledNumber
import com.strangenaut.attendance.core.ui.theme.SurfaceShape

@Composable
fun StatisticsButton(
    label: String,
    attendanceLabel: String,
    hostingLabel: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Surface(
        shape = SurfaceShape,
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.statistics_selected),
                    contentDescription = label
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                LabeledNumber(
                    number = 0,
                    label = attendanceLabel,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .weight(1f)
                )
                LabeledNumber(
                    number = 0,
                    label = hostingLabel,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .weight(1f)
                )
            }
        }
    }
}

@Preview
@Composable
private fun StatisticsButtonPreview() {
    StatisticsButton(
        attendanceLabel = "занятий посещено",
        hostingLabel = "занятий проведено",
        label = "Статистика",
        onClick = {}
    )
}