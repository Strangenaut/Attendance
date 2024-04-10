package com.strangenaut.attendance.home.presentation.host.util

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.strangenaut.attendance.home.domain.util.QrEncoder

fun String.encodeToQrCodeImageBitmap(size: Int): ImageBitmap {
    val bits = QrEncoder.encodeToQrCodeBitMatrix(this, size)
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)

    val unitColor = Color.BLACK
    val zeroColor = Color.TRANSPARENT

    bitmap.setHasAlpha(true)

    for (x in 0 until size) {
        for (y in 0 until size) {
            bitmap.setPixel(x, y, if (bits[x, y]) unitColor else zeroColor)
        }
    }

    return bitmap.asImageBitmap()
}