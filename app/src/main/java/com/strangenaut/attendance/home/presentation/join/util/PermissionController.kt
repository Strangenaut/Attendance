package com.strangenaut.attendance.home.presentation.join.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PermissionController(
    private val context: Context,
    private val requiredPermission: String
) {

    private val _permissionGranted = MutableStateFlow(false)
    val permissionGranted: StateFlow<Boolean> get() = _permissionGranted

    init {
        updatePermissionInfo()
    }

    @Composable
    fun MultiplePermissionsLauncher() {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                _permissionGranted.update{
                    isGranted
                }
            }
        )

        LaunchedEffect(key1 = true) {
            launcher.launch(requiredPermission)
        }
    }

    private fun updatePermissionInfo() {
        val isGranted = hasRequiredPermission(requiredPermission)

        _permissionGranted.update {
            isGranted
        }
    }

    private fun hasRequiredPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}