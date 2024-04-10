package com.strangenaut.attendance.home.presentation.join.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.presentation.components.TopBar
import com.strangenaut.attendance.home.presentation.components.IconTextButtonWithDescription
import com.strangenaut.attendance.home.presentation.components.StatisticsButton

@Composable
fun Options(
    onNavigateToStatistics: () -> Unit,
    onNavigateToJoinLesson: () -> Unit,
    onNavigateToHostLesson: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar {
                Image(
                    painter = painterResource(R.drawable.app_icon_small),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            StatisticsButton(
                label = "Статистика",
                attendanceLabel = "занятий посещено",
                hostingLabel = "занятий проведено",
                modifier = Modifier.padding(16.dp),
                onClick = onNavigateToStatistics
            )
            IconTextButtonWithDescription(
                iconPainter = painterResource(R.drawable.connect),
                title = "Присоединиться",
                description = "Вы можете присоединиться, приложив ваш телефон к телефону " +
                        "преподавателя или просканировав QR код. Если у вас отсутствует " +
                        "интернет - соединение или вы столкнулись с другими проблемами, " +
                        "попросите преподавателя отметить вас на занятии вручную",
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = onNavigateToJoinLesson
            )
            IconTextButtonWithDescription(
                iconPainter = painterResource(R.drawable.add),
                title = "Начать занятие",
                description = "Выберите существующий предмет или добавьте новый, чтобы начать " +
                        "сессию. Вы можете настраивать параметры сессии и добавлять учеников вручную",
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = onNavigateToHostLesson
            )
        }
    }
}

@Preview
@Composable
fun OptionsPreview() {
    Options(
        onNavigateToStatistics = {},
        onNavigateToJoinLesson = {},
        onNavigateToHostLesson = {}
    )
}