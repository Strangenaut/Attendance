package com.strangenaut.attendance.settings.domain.usecases

import com.strangenaut.attendance.core.domain.model.ThemeMode
import com.strangenaut.attendance.settings.domain.repository.SettingsRepository

class SaveThemeMode(private val repository: SettingsRepository) {

    suspend operator fun invoke(themeMode: ThemeMode) {
        repository.saveThemeMode(themeMode)
    }
}