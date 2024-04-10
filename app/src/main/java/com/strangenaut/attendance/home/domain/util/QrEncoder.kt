package com.strangenaut.attendance.home.domain.util

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

object QrEncoder {

    fun encodeToQrCodeBitMatrix(text: String, size: Int): BitMatrix {
        val hints = hashMapOf<EncodeHintType, Int>().apply {
            this[EncodeHintType.MARGIN] = 1
        }
        val writer = QRCodeWriter()

        return writer.encode(text, BarcodeFormat.QR_CODE, size, size, hints)
    }
}