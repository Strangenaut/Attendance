package com.strangenaut.attendance.home.presentation.host.components

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.core.ui.theme.SurfaceShape
import com.strangenaut.attendance.home.presentation.host.util.encodeToQrCodeImageBitmap

@Composable
fun QrCodeIcon(
    text: String
) {
    val system = Resources.getSystem()

    val screenWidth = system.configuration.screenWidthDp
    val size = (screenWidth * 0.75).toInt()

    Surface(
        shape = SurfaceShape,
        shadowElevation = 4.dp,
        modifier = Modifier.size(size.dp)
    ) {
        Icon(
            bitmap = text.encodeToQrCodeImageBitmap(size = size),
            contentDescription = "QR code",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun QrIconPreview() {
    QrCodeIcon("example@mail com:01 01 1970 00:00|2^jPUSP%GkVbzSb3")
}