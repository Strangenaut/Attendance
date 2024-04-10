package com.strangenaut.attendance.home.presentation.join

import android.Manifest
import android.widget.Toast
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.strangenaut.attendance.core.components.LabeledTopBar
import com.strangenaut.attendance.home.data.QrCodeAnalyzer
import com.strangenaut.attendance.home.presentation.join.util.PermissionController

@Composable
fun JoinLesson(
    onNavigateBack: () -> Unit
) {
    val recognitionResult = MutableLiveData("")

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

    recognitionResult.observe(lifecycleOwner) {
        val result = it.trim()

        if (result.isNotEmpty()) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show()
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            LabeledTopBar(
                label = "Присоединиться",
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
                text = "Просканируйте QR-код на устройстве преподавателя",
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
                        QrCodeAnalyzer { result ->
                            recognitionResult.postValue(result)
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