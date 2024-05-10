package com.strangenaut.attendance.settings.domain.usecases

import com.strangenaut.attendance.core.domain.model.ThemeMode
import com.strangenaut.attendance.settings.domain.repository.SettingsRepository

class LoadThemeMode(private val repository: SettingsRepository) {

    operator fun invoke(): ThemeMode {
        return repository.loadThemeMode()
    }
}