package com.strangenaut.attendance.settings.domain.repository

import com.strangenaut.attendance.core.domain.model.ThemeMode

interface SettingsRepository {

    suspend fun saveThemeMode(themeMode: ThemeMode)

    fun loadThemeMode(): ThemeMode
}