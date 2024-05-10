package com.strangenaut.attendance.settings.domain.usecases

data class SettingsUseCases (
    val signOut: SignOut,
    val saveThemeMode: SaveThemeMode,
    val loadThemeMode: LoadThemeMode
)