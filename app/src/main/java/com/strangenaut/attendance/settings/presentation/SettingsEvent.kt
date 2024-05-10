package com.strangenaut.attendance.settings.presentation

import com.strangenaut.attendance.core.domain.model.ThemeMode

sealed class SettingsEvent {

    data class SelectTheme(val themeMode: ThemeMode): SettingsEvent()

    data object SignOut: SettingsEvent()
}