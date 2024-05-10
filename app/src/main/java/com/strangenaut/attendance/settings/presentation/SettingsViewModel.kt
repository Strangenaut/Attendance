package com.strangenaut.attendance.settings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strangenaut.attendance.settings.domain.usecases.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        val themeMode = settingsUseCases.loadThemeMode()
        _state.update {
            it.copy(themeMode = themeMode)
        }
    }

    fun onEvent(event: SettingsEvent) {
        viewModelScope.launch {
            try {
                executeEvent(event)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    private suspend fun executeEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.SelectTheme -> {
                _state.update {
                    it.copy(themeMode = event.themeMode)
                }
                settingsUseCases.saveThemeMode(event.themeMode)
            }
            is SettingsEvent.SignOut -> {
                settingsUseCases.signOut()
            }
        }
    }
}