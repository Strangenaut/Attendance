package com.strangenaut.attendance.core

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.strangenaut.attendance.auth.presentation.AuthViewModel
import com.strangenaut.attendance.core.navigation.MainNavGraph
import com.strangenaut.attendance.core.presentation.util.printMessage
import com.strangenaut.attendance.core.ui.theme.AttendanceTheme
import com.strangenaut.attendance.core.domain.model.ThemeMode
import com.strangenaut.attendance.settings.presentation.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val collectedSettingsState = settingsViewModel.state.collectAsState()
            val settingsState by remember {
                mutableStateOf(collectedSettingsState)
            }
            val darkTheme = when(settingsState.value.themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            authViewModel.error.observe(this) { message ->
                printMessage(this, message)
            }
            settingsViewModel.error.observe(this) { message ->
                printMessage(this, message)
            }

            AttendanceTheme(darkTheme = darkTheme) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    MainNavGraph(
                        authViewModel = authViewModel,
                        settingsViewModel = settingsViewModel
                    )
                }
            }
        }
    }
}