package com.strangenaut.attendance.settings.di

import android.content.Context
import android.content.SharedPreferences
import com.strangenaut.attendance.AttendanceApp
import com.strangenaut.attendance.auth.domain.repository.AuthRepository
import com.strangenaut.attendance.settings.data.repository.SettingsRepositoryImpl
import com.strangenaut.attendance.settings.domain.repository.SettingsRepository
import com.strangenaut.attendance.settings.domain.usecases.LoadThemeMode
import com.strangenaut.attendance.settings.domain.usecases.SaveThemeMode
import com.strangenaut.attendance.settings.domain.usecases.SettingsUseCases
import com.strangenaut.attendance.settings.domain.usecases.SignOut
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    private const val SETTINGS_SP_NAME = "user_settings"

    @Provides
    @Singleton
    fun provideSharedPreferences(app: AttendanceApp): SharedPreferences {
        return app.getSharedPreferences(SETTINGS_SP_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        sharedPreferences: SharedPreferences
    ): SettingsRepository {
        return SettingsRepositoryImpl(sharedPreferences = sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSettingsUseCases(
        authRepository: AuthRepository,
        settingsRepository: SettingsRepository
    ): SettingsUseCases {
        return SettingsUseCases(
            signOut = SignOut(authRepository),
            saveThemeMode = SaveThemeMode(settingsRepository),
            loadThemeMode = LoadThemeMode(settingsRepository)
        )
    }
}