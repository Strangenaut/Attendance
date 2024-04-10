package com.strangenaut.attendance.core.presentation.model

import androidx.compose.ui.graphics.painter.Painter

data class TabBarItem(
    val title: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
)