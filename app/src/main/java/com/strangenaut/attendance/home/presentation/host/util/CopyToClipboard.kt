package com.strangenaut.attendance.home.presentation.host.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.strangenaut.attendance.R

fun String.copyToClipBoard(context: Context, label: String) {
    val clipboardManager = ContextCompat.getSystemService(context, ClipboardManager::class.java)
    val clip = ClipData.newPlainText(label, this)
    clipboardManager?.setPrimaryClip(clip)

    val copiedMessage = context.getString(R.string.copied_to_clipboard)
    Toast.makeText(context, copiedMessage, Toast.LENGTH_LONG).show()
}