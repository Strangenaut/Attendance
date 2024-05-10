package com.strangenaut.attendance.home.presentation.components

import android.content.res.Resources
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strangenaut.attendance.R
import com.strangenaut.attendance.core.ui.theme.SurfaceShape
import com.strangenaut.attendance.home.presentation.host.util.encodeToQrCodeImageBitmap

@Composable
fun QrCodeIcon(text: String?) {
    val system = Resources.getSystem()

    val screenWidth = system.configuration.screenWidthDp
    val size = (screenWidth * 0.75).toInt()

    Surface(
        shape = SurfaceShape,
        shadowElevation = 4.dp,
        color = Color.White,
        modifier = Modifier.size(size.dp)
    ) {
        if (text == null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.updating_qr),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            return@Surface
        }

        Icon(
            bitmap = text.encodeToQrCodeImageBitmap(size = size),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun QrIconPreview() {
    val text = "example@mail com:01 01 1970 00:00|2^jPUsP%GkVbzSb3"
    QrCodeIcon(text)
}