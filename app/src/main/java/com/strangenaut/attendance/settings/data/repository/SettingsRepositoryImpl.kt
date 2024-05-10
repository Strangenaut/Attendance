package com.strangenaut.attendance.settings.data.repository

import android.content.SharedPreferences
import com.strangenaut.attendance.core.domain.model.ThemeMode
import com.strangenaut.attendance.settings.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SettingsRepository {

    override suspend fun saveThemeMode(themeMode: ThemeMode) {
        sharedPreferences.edit().apply {
            putString(THEME_MODE_SETTINGS_KEY, themeMode.name)
            apply()
        }
    }

    override fun loadThemeMode(): ThemeMode {
        val defaultValue = ThemeMode.SYSTEM
        val result = sharedPreferences.getString(THEME_MODE_SETTINGS_KEY, defaultValue.name)
        return when (result) {
            "LIGHT" -> ThemeMode.LIGHT
            "DARK" -> ThemeMode.DARK
            "SYSTEM" -> ThemeMode.SYSTEM
            else -> ThemeMode.SYSTEM
        }
    }

    companion object {

        private const val THEME_MODE_SETTINGS_KEY = "theme_mode"
    }
}