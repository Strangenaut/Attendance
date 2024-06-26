package com.strangenaut.attendance.home.presentation.join

import android.Manifest
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.presentation.components.LabeledTopBarWithNavBack
import com.strangenaut.attendance.home.presentation.join.util.QrCodeAnalyzer
import com.strangenaut.attendance.core.domain.model.Credentials
import com.strangenaut.attendance.home.presentation.join.util.PermissionController

@Composable
fun Join(
    onJoinLesson: (credentials: Credentials) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    val cameraPermissionController = PermissionController(context, Manifest.permission.CAMERA)
    val permissionState = cameraPermissionController.permissionGranted.collectAsState()
    val hasCameraPermission by remember {
        mutableStateOf(permissionState)
    }

    if (!hasCameraPermission.value) {
        cameraPermissionController.MultiplePermissionsLauncher()
    }

    Scaffold(
        topBar = {
            LabeledTopBarWithNavBack(
                label = stringResource(R.string.join),
                onNavigateBack = onNavigateBack
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (!hasCameraPermission.value) {
                return@Column
            }

            Text(
                text = stringResource(R.string.scan_qr),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            AndroidView(
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = Preview.Builder().build()
                    val selector = CameraSelector
                        .Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    val imageAnalysis = ImageAnalysis
                        .Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        QrCodeAnalyzer { credentials ->
                            val emptyCredentials = Credentials()

                            if (credentials != emptyCredentials) {
                                onJoinLesson(credentials)
                                onNavigateBack()
                            }
                        }
                    )

                    try {
                        cameraProviderFuture
                            .get()
                            .bindToLifecycle(
                                lifecycleOwner,
                                selector,
                                preview,
                                imageAnalysis
                            )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    previewView
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        horizontal = 100.dp,
                        vertical = 16.dp
                    )
            )
        }
    }
}