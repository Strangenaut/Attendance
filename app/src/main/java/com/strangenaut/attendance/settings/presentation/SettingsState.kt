package com.strangenaut.attendance.settings.presentation

import com.strangenaut.attendance.core.domain.model.ThemeMode

data class SettingsState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM
)