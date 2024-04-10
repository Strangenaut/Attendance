package com.strangenaut.attendance.core

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.strangenaut.attendance.auth.presentation.AuthViewModel
import com.strangenaut.attendance.core.navigation.NavGraph
import com.strangenaut.attendance.core.ui.theme.AttendanceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            authViewModel.error.observe(this) { message ->
                if (message.isNotEmpty()) {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }

            AttendanceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(authViewModel = authViewModel)
                }
            }
        }
    }
}